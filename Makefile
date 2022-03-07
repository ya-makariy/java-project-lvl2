.PHONY: install
install:
	./gradlew clean install

.PHONY: run-dist
run-dist:
	./build/install/app/bin/app

.PHONY: run
run:
	./gradlew run

.PHONY: check-updates
check-updates:
	./gradlew dependencyUpdates

.PHONY: lint
lint:
	./gradlew checkstyleMain

.PHONY: build
build:
	./gradlew clean build

.PHONY: test
test:
	./gradlew clean test