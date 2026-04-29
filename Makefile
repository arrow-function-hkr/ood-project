# helper functions 
# ========================

# Apply wildcard recursively in the given directory

rwildcard=$(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2) $(filter $(subst *,%,$2),$d))

# ========================

SOURCES = $(call rwildcard,src/,*.java)

BUILD_DIR = .build

# Entry point class/'base filename'
# relative to ./src/
ENTRY_POINT_BASE = Main

default: compile execute

clean:
	@[ -d "$(BUILD_DIR)" ] && { \
		rm -r "$(BUILD_DIR)" && echo "Cleaned '$(BUILD_DIR)/'"; \
	} || true

# Compile the java source code into bytecode
compile:
	@javac -d "$(BUILD_DIR)" $(SOURCES)

# execute the compiled java bytecode with potimization applied
execute:
	@[ -d "$(BUILD_DIR)" ] && \
		java -cp "$(BUILD_DIR)" -XX:+TieredCompilation -XX:TieredStopAtLevel=4 $(ENTRY_POINT_BASE)

# Run the source java code directly (compilation under the hood)
run:
	@java "./src/$(ENTRY_POINT_BASE).java"


