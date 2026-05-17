# Holds 1 if the running OS is windows, 0 otherwise (branching for shell commands)
IS_WIN := $(if $(filter Windows_NT,$(OS)),1,0)

# Dynamic command invocation based on the OS
# Usage:
# target-name:
# 	$(PS) "simple powershell command that will be executed on Windows"
# 	$(CMD_SH) "simple cmd command that will be executed on Windows"
# 	$(UNIX) "unix-like command that will be executed on Unix-like OSes"
#
# Note! $(PS) and $(CMD_SH) doesn't escape the command that it receives. If the command is complex enough, use:
# $(call RUN_PS , [command to execute] ) / $(call RUN_CMD , [command to execute] )
ifeq ($(IS_WIN),1)
# Windows
PS := @powershell -Command
UNIX := @exit 0 \# @REM
CMD_SH = @cmd /c
CLASS_PATH_DIVIDER := ;
DD := \\
else
# Unix-like
PS := @\#
UNIX := @
CMD_SH = @exit 0 \#
CLASS_PATH_DIVIDER := :
DD := /
endif

# ========================
# helper functions 

# Apply wildcard recursively in the given directory
# Usage: $(call rwildcard,[directory where to look],[glob to apply])
define rwildcard
$(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2) $(filter $(subst *,%,$2),$d))
endef

define RUN_PS
$(PS) "& { $(1) }"
endef

define RUN_CMD
$(CMD_SH) "$(1)"
endef

# Removes the leading '@' in front of the command (that suppresses the output of the command that will be invoked by the Make)
# Usage:
# $(call remove_suppress,$(PS) powershell command)
# $(call remove_suppress,$(CMD_SH) cmd command)
# $(call remove_suppress,$(call RUN_PS,powershell command))
# $(call remove_suppress,$(call RUN_CMD,cmd command))
define remove_suppress
$(subst @,,$(1))
endef

# Show a simple string to the output
# Usage:
# $(call ECHO,message to show)
ifeq ($(IS_WIN),1)
define ECHO
$(call RUN_CMD,echo $(1))
endef
else
define ECHO
@echo $(1)
endef
endif


# ========================


# Where all compiled source application files will be placed
BUILD_DIR := .build
# Where all compiled testing files will be placed
TEST_BUILD_DIR := $(BUILD_DIR)

# Entry point base filename relative to ./src/
# e.g.: Main, onion/lifeproducts/rms/Main, custom/App
ENTRY_POINT_BASE_PATH := Main
# Entry point base class name relative to ./src/
# e.g.: Main, onion.lifeproducts.rms.Main, custom.App
ENTRY_POINT_BASE_CLASS := Main

# Name of the directory where all libraries/dependencies will be placed
LIBS_DIR_NAME := lib
# Name of the JUnit .jar file that will be downloaded into lib/ directory
JUNIT_JAR_FILE := junit-platform-console-standalone-6.0.0.jar

JUNIT_DOWNLOAD_URL := https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.0.0/$(JUNIT_JAR_FILE)


# some ANSI graphics
ESC := 
FG_RED := $(ESC)[31m
FG_GREEN := $(ESC)[32m
FG_YELLOW := $(ESC)[33m
FG_BLUE := $(ESC)[34m
FG_MAGENTA := $(ESC)[35m
FG_CYAN := $(ESC)[36m
FG_WHITE := $(ESC)[37m
FG_GRAY := $(ESC)[38;5;248m
CA := $(ESC)[0m
ANSI_BOLD := $(ESC)[1m


COMMA := ,
EMPTY :=
SPACE := $(EMPTY) $(EMPTY)



#################
### Execution ###
#################

# First defined target, will be executed if make is run without targets
default: compile execute

clean-java: clean

clean: __clean__success_msg := [Java] Cleaned '$(FG_YELLOW)$(BUILD_DIR)/$(CA)'
clean: __clean_nothing_to_clean_msg := [Java] Nothing to clean.
clean:
	$(call RUN_CMD, if exist $(BUILD_DIR) ( rmdir /s /q $(BUILD_DIR) && echo $(__clean__success_msg)) else ( echo $(__clean_nothing_to_clean_msg) ))
	$(UNIX) [ -d "$(BUILD_DIR)" ] && { \
			rm -r "$(BUILD_DIR)" && echo "$(__clean__success_msg)"; \
		} || echo "$(__clean_nothing_to_clean_msg)"
	

clean-all: clean clean-test clean-lib


# Compile the java source code into bytecode
compile: ALL_SOURCES := $(call rwildcard,src/,*.java)
# only project-relates source java files, no tests
compile: SOURCES := $(filter-out src/test/%, $(ALL_SOURCES))
compile: __compile__verbose_msg := [Java] Compiling...
compile:
ifeq ($(verbose), true)
	$(call ECHO,$(__compile__verbose_msg))
endif
	@javac -d "$(BUILD_DIR)" $(SOURCES)

# execute the compiled java bytecode with potimization applied
execute: __execute__fail__no_build_dir := Build directory doesn't exist: $(FG_YELLOW)$(BUILD_DIR)/$(CA)
execute: __execute__java_run_command := java -cp $(BUILD_DIR) -XX:+TieredCompilation -XX:TieredStopAtLevel=4 $(ENTRY_POINT_BASE_CLASS)
execute:
ifeq ($(verbose), true)
	$(call ECHO,[Java] Executing...)
endif
	$(call RUN_CMD, if not exist $(BUILD_DIR) ( echo $(__execute__fail__no_build_dir) ) else ( $(__execute__java_run_command) ))
	$(UNIX) [ -d '$(BUILD_DIR)' ] && \
		{ $(__execute__java_run_command) ; exit $$? ; } \
		|| echo "$(__execute__fail__no_build_dir)"
	

# Run the source java code directly (compilation under the hood)
run:
	@java "./src/$(ENTRY_POINT_BASE_PATH).java"


#############
### Tests ###
#############

clean-test: __clean-test__success_msg := [Test] Cleaned '$(FG_YELLOW)$(TEST_BUILD_DIR)/$(CA)'
clean-test: __clean-test_nothing_to_clean_msg := [Test] Nothing to clean.
clean-test:
	$(call RUN_CMD, if exist $(TEST_BUILD_DIR) ( rmdir /s /q $(TEST_BUILD_DIR) && echo $(__clean-test__success_msg) ) else ( echo $(__clean-test_nothing_to_clean_msg) ))
	$(UNIX) [ -d "$(BUILD_DIR)" ] && { \
		rm -r "$(BUILD_DIR)" && echo "$(__clean-test__success_msg)"; \
	} || echo "$(__clean-test_nothing_to_clean_msg)"

clean-lib: __clean-lib_success_msg = [Libs] Cleaned '$(FG_YELLOW)$(LIBS_DIR_NAME)/$(CA)'
clean-lib: __clean-lib_nothing_to_clean_msg := [Libs] Nothing to clean.
clean-lib:
	$(call RUN_CMD, if exist $(LIBS_DIR_NAME) ( rmdir /s /q $(LIBS_DIR_NAME) && echo $(__clean-lib_success_msg) ) else ( echo $(__clean-lib_nothing_to_clean_msg) ))
	$(UNIX) [ -d "$(LIBS_DIR_NAME)" ] && \
		{ rm -r "$(LIBS_DIR_NAME)" && echo "$(__clean-lib_success_msg)" ; } || \
		echo "$(__clean-lib_nothing_to_clean_msg)"

test-full: compile get-junit test-compile test-execute
# alias
full-test: test-full

test: get-junit test-compile test-execute

test-compile: SOURCES := $(call rwildcard,src/test,*.java)
test-compile: get-junit
ifneq ($(silent), true)
	$(call ECHO,[Test] Compiling...)
endif
	@javac -cp "$(LIBS_DIR_NAME)/*$(CLASS_PATH_DIVIDER)$(BUILD_DIR)" -d "$(TEST_BUILD_DIR)" $(SOURCES)


test-execute: EXPERIMENTAL_JAVA_FLAGS := --enable-final-field-mutation=ALL-UNNAMED
# it's hard to get rid of extra verbose output when the test fails, so just get used to it
# '$(EXPERIMENTAL_JAVA_FLAGS)' flag works only on Java 26+, so there is a check whether this flag is supported or not
# tree-sitter for syntax highlighting goes crazy on next 4 lines, but they are containing correct syntax
ifeq ($(IS_WIN),1)
test-execute: __test-execute__java_args := $(shell $(call remove_suppress,$(call RUN_CMD, java $(EXPERIMENTAL_JAVA_FLAGS) -version > nul 2>&1 && echo $(EXPERIMENTAL_JAVA_FLAGS))))
else
test-execute: __test-execute__java_args := $(shell sh -c "set +e; java $(EXPERIMENTAL_JAVA_FLAGS) -version > /dev/null 2>&1 && echo '$(EXPERIMENTAL_JAVA_FLAGS)' || echo ''")
endif
test-execute: get-junit
ifneq ($(silent), true)
	$(call ECHO,[Test] Executing...)
endif
	@java \
		$(__test-execute__java_args) \
		-jar "$(LIBS_DIR_NAME)$(DD)$(JUNIT_JAR_FILE)" execute \
		--class-path "$(BUILD_DIR)$(CLASS_PATH_DIVIDER)$(TEST_BUILD_DIR)" \
		--scan-class-path \
		--disable-banner


ifeq ($(verbose),true)
test-check-libs-dir: __test-check-libs-dir__verbose__creating_dir_msg := [Test] Creating test build directory: $(FG_YELLOW)$(LIBS_DIR_NAME)$(DD)$(CA)
endif
test-check-libs-dir:
	$(call RUN_CMD, \
		if not exist $(LIBS_DIR_NAME) ( \
			(if /i '$(verbose)'=='true' ( echo $(__test-check-libs-dir__verbose__creating_dir_msg) ) ) & \
			mkdir $(LIBS_DIR_NAME) \
		) \
	)
	$(UNIX) [ -d "$(LIBS_DIR_NAME)" ] || { \
		[ "$(verbose)" = true ] && echo "$(__test-check-libs-dir__verbose__creating_dir_msg)" ; \
		mkdir "$(LIBS_DIR_NAME)" ; \
	}

get-junit: test-check-libs-dir
	$(call RUN_CMD, if not exist $(LIBS_DIR_NAME)$(DD)$(JUNIT_JAR_FILE) ( $(MAKE) -s __safe=true __get_junit_jar & exit /b %ERRORLEVEL% ))
	$(UNIX) [ ! -f "$(LIBS_DIR_NAME)$(DD)$(JUNIT_JAR_FILE)" ] && \
		{ $(MAKE) -s __safe=true __get_junit_jar 2>/dev/null ; exit $$? ; } || \
		true

__get_junit_jar: __get_junit_jar__not_safe_from_interactive_shell := [$(FG_YELLOW)Warning$(CA)]: This target is not intented to be run directly from the interactive shell.
ifeq ($(verbose),true)
__get_junit_jar: __get_junit_jar__verbose_download_msg := [Test] Downloading $(FG_YELLOW)$(JUNIT_JAR_FILE)$(CA) file...
endif
__get_junit_jar: __get_junit_jar__win__download_cmd__bitsadmin := bitsadmin /transfer 'DownloadJunitJar' /download /priority normal $(JUNIT_DOWNLOAD_URL) %CD%$(DD)$(LIBS_DIR_NAME)$(DD)$(JUNIT_JAR_FILE)
__get_junit_jar: __get_junit_jar__win__download_cmd__powershell := $(PS) Invoke-WebRequest -Uri '$(JUNIT_DOWNLOAD_URL)' -OutFile '$(LIBS_DIR_NAME)$(DD)$(JUNIT_JAR_FILE)'
__get_junit_jar: __get_junit_jar__win__download_cmd__curl := curl -L -o $(LIBS_DIR_NAME)$(DD)$(JUNIT_JAR_FILE) $(JUNIT_DOWNLOAD_URL)


# Method and command to execute on Windows to download the JUnit .jar file:
#   Method   | Command
#     ''     | powershell
#     1      | powershell
# powershell | powershell
#     2      | bitsadmin
# bitsadmin  | bitsadmin
#     3      | curl
#    curl    | curl
# Note! Doesn't affect Unix-like environment. Only for windows
__get_junit_jar__valid_methods := 1 powershell 2 bitsadmin 3 curl

define __get_junit_jar__valid_methods_msg
Unknown method: $(FG_YELLOW)$(method)$(CA). Valid methods: $(subst $(SPACE),$(COMMA)$(SPACE),$(foreach m,$(__get_junit_jar__valid_methods),$(FG_GREEN)$(m)$(CA)))
If other methods doesn't work, you can download the JUnit .jar file directly from the source via other methods:
$(JUNIT_DOWNLOAD_URL)
endef

ifeq ($(method),)
__get_junit_jar: __get_junit_jar__win__download_cmd := $(__get_junit_jar__win__download_cmd__powershell)
else ifeq ($(method),1)
__get_junit_jar: __get_junit_jar__win__download_cmd := $(__get_junit_jar__win__download_cmd__powershell)
else ifeq ($(method),powershell)
__get_junit_jar: __get_junit_jar__win__download_cmd := $(__get_junit_jar__win__download_cmd__powershell)
else ifeq ($(method),2)
__get_junit_jar: __get_junit_jar__win__download_cmd := $(__get_junit_jar__win__download_cmd__bitsadmin)
else ifeq ($(method),bitsadmin)
__get_junit_jar: __get_junit_jar__win__download_cmd := $(__get_junit_jar__win__download_cmd__bitsadmin)
else ifeq ($(method),3)
__get_junit_jar: __get_junit_jar__win__download_cmd := $(__get_junit_jar__win__download_cmd__curl)
else ifeq ($(method),curl)
__get_junit_jar: __get_junit_jar__win__download_cmd := $(__get_junit_jar__win__download_cmd__curl)
else
__get_junit_jar: __get_junit_jar__win__download_cmd := $(CMD_SH) \
	$(subst echo  ,echo off,\
		$(foreach l,$(subst $(SPACE),$(ESC)|$(ESC),$(__get_junit_jar__valid_methods_msg)),echo $(subst $(ESC)|$(ESC),$(SPACE),$(l)) & )\
	) echo off
endif

__get_junit_jar:
ifeq ($(IS_WIN),1)
	$(call RUN_CMD, \
		(if not '$(__safe)'=='true' ( echo $(__get_junit_jar__not_safe_from_interactive_shell) & exit /b 1 )) & \
		(if '$(verbose)'=='true' (echo $(__get_junit_jar__verbose_download_msg))) & \
		$(__get_junit_jar__win__download_cmd) \
	)
else
# this one was so annoying on windows, so i extracted it into a separate Make branch,
# since both, CMD and POWESHELL suck as hell in syntax
	[ '$(__safe)' != true ] && { \
		echo '$(__get_junit_jar__not_safe_from_interactive_shell)' ; \
		exit 1; \
	} || \
	if command -v curl >/dev/null 2>&1; then \
		[ '$(verbose)' = true ] && echo '$(__get_junit_jar__verbose_download_msg)'; \
		curl -L -o '$(LIBS_DIR_NAME)/$(JUNIT_JAR_FILE)' '$(JUNIT_DOWNLOAD_URL)'; \
	elif command -v wget >/dev/null 2>&1; then \
		[ '$(verbose)' = true ] && echo '$(__get_junit_jar__verbose_download_msg)'; \
		wget -O '$(LIBS_DIR_NAME)/$(JUNIT_JAR_FILE)' '$(JUNIT_DOWNLOAD_URL)'; \
	else \
		echo 'No download tool found!'; \
		echo 'Consider installing either $(FG_YELLOW)curl$(CA) or $(FG_YELLOW)wget$(CA) or install the JUnit framework .jar file by yourself from this URL:\n'; \
		echo '$(JUNIT_DOWNLOAD_URL)'; \
		echo '\ninto the $(LIBS_DIR_NAME)/ directory.'; \
		exit 1; \
	fi
endif
