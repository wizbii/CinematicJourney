#!/bin/bash -e

# Constants & Variables
VERSION_FILE=build.gradle.kts

# Functions
function isVersionValid {
    local VERSION_PARAM=$1
    if [[ -n "$VERSION_PARAM"  ]] && [[ ! "$VERSION_PARAM" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
        echo "Version '$VERSION_PARAM' does not match pattern major.minor.fixes"
        exit 1
    fi
}

function readVersion {
    local CURRENT_VERSION=$(grep "val appVersionName = " ${VERSION_FILE} | xargs echo | cut -d ' ' -f 4)
    echo "${CURRENT_VERSION}"
}

function setVersion {
    local VERSION="$1"
    sed -i '' -E "s/(val appVersionName = )\".+\"/\1\"$VERSION\"/" ${VERSION_FILE}
}

function getNextSnapshotVersion {
    local VERSION="$1"
    local MAJOR=${VERSION%%.*}
    local MINOR=${VERSION%.*}
    local MINOR=${MINOR##*.}
    local FIXES=${VERSION##*.}
    local NEW_FIXES=$((FIXES+1))
    echo "$MAJOR.$MINOR.$NEW_FIXES-SNAPSHOT"
}

# Checks

## Check that a version parameter was provided and that it's the correct format
VERSION_PARAM=$1
if ! isVersionValid "${VERSION_PARAM}"; then
    exit 1
fi

## Check that the current git branch is develop
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
if [[ "$CURRENT_BRANCH" != "develop" ]]; then
    echo "Current branch is '$CURRENT_BRANCH', should be 'develop'"
    exit 1
fi

## Check that gitflow has been configured
if ! grep -q gitflow .git/config; then
    echo "It looks like gitflow is not configured"
    exit 1
fi

## Get and check current version
CURRENT_VERSION=$(readVersion)
if [[ "$CURRENT_VERSION" != *"-SNAPSHOT" ]]; then
    echo "The current version ($CURRENT_VERSION) doesn't end with -SNAPSHOT"
    exit 1
fi

# Release process

## Prepare parameters
CURRENT_VERSION=$(readVersion)
VERSION_RELEASE=$(test -z "${VERSION_PARAM}" && echo "${CURRENT_VERSION%-*}" || echo "${VERSION_PARAM}")
VERSION_SNAPSHOT=$(getNextSnapshotVersion "${VERSION_RELEASE}")

## Explain what will happen
echo
echo "Current version: $CURRENT_VERSION"
echo "Release version: $VERSION_RELEASE"
echo "Working version: $VERSION_SNAPSHOT"
echo
echo -ne "\rHit Ctrl-C if something's wrong, release in 3"
sleep 1
echo -ne "\rHit Ctrl-C if something's wrong, release in 2"
sleep 1
echo -ne "\rHit Ctrl-C if something's wrong, release in 1"
sleep 1
echo -ne "\rHit Ctrl-C if something's wrong, release in 0"
sleep 1
echo
echo

## Execute release

echo "Pulling main"
git checkout main
git pull
echo

echo "Pulling develop"
git checkout develop
git pull
echo

echo "Starting Git Flow Release $VERSION_RELEASE"
git flow release start "${VERSION_RELEASE}"
echo

echo "Setting version in $VERSION_FILE to $VERSION_RELEASE"
setVersion "${VERSION_RELEASE}"
echo

echo "Syncing XCode app version"
./gradlew setXcodeVersion
echo

echo "Committing changes to $VERSION_FILE"
git add ${VERSION_FILE} xcode
git commit -m "Set version to $VERSION_RELEASE for release"
echo

echo "Finishing Git Flow Release $VERSION_RELEASE"
export GIT_MERGE_AUTOEDIT=no
git flow release finish -m "Tagging version $VERSION_RELEASE"
unset GIT_MERGE_AUTOEDIT
echo

echo "Setting version in $VERSION_FILE to $VERSION_SNAPSHOT"
setVersion "${VERSION_SNAPSHOT}"
echo

echo "Syncing XCode app version"
./gradlew setXcodeVersion
echo

echo "Committing changes to $VERSION_FILE"
git add ${VERSION_FILE} xcode
git commit -m "Set version to $VERSION_SNAPSHOT for development"
echo

echo "Pushing main"
git checkout main
git push -u origin main
echo

echo "Pushing develop"
git checkout develop
git push -u origin develop
echo

echo "Pushing tags"
git push origin --tags
echo
