SUMMARY="Gaia Demo image"

require recipes-core/images/core-image-minimal.bb

inherit features_check extrausers userfs


IMAGE_FEATURES ?= ""
IMAGE_LINGUAS ?= ""

IMAGE_EXTRA_INSTALL ?= ""

IMAGE_INSTALL += " \
        packagegroup-base \
        ${@bb.utils.contains('BBFILE_COLLECTIONS', 'swupdate', 'add-update-files', '', d)} \
        evtest \
        gdbserver \
        i2c-tools \
        libgpiod-tools \
        mtd-utils \
        nano \
        production-tool \
        rng-tools \
        ${IMAGE_EXTRA_INSTALL} \
 	curl \
	nano \
	bash \
	picocom \
	iotedge-cli \
	iotedge-daemon \
	libiothsm-std \
	cargo \
	docker-moby \
	initscripts \
	htop \
	alsa-utils \
 "

# If U-Boot is not included into the rootfs itself, we still need it for
# creating images with wic. Therefore we need a dependency for it.
do_image_wic[depends] += "virtual/bootloader:do_deploy"

do_deploy[vardepsexclude] = "DATETIME"

# if debug-tweaks is unset, set a root password (default is 'root')
ROOT_PWD ?= "\$1\$/nrGReFp\$Emef9p70yRoahjHctKZ2p0"
EXTRA_USERS_PARAMS = "${@bb.utils.contains('IMAGE_FEATURES', 'debug-tweaks', '', 'usermod -p \'${ROOT_PWD}\' root;', d)}"

# reserve extra space of 1000 MB in rootfs image
IMAGE_ROOTFS_EXTRA_SPACE = "1048576"