require mender-client.inc

################################################################################
#-------------------------------------------------------------------------------
# THINGS TO CONSIDER FOR EACH RELEASE:
# - SRC_URI (particularly "branch")
# - SRCREV
# - DEFAULT_PREFERENCE
#-------------------------------------------------------------------------------

SRC_URI = "git://github.com/mendersoftware/mender;protocol=https;branch=3.1.x"

# Tag: 3.1.0
SRCREV = "4c077c92f313aac92bf3bb492ef479b3447ddc08"

# Enable this in Betas, and in branches that cannot carry this major version as
# default.
# Downprioritize this recipe in version selections.
#DEFAULT_PREFERENCE = "-1"

################################################################################

# DO NOT change the checksum here without make sure that ALL licenses (including
# dependencies) are included in the LICENSE variable below. Note that for
# releases, we must check the LIC_FILES_CHKSUM.sha256 file, not the LICENSE
# file.
LIC_FILES_CHKSUM = "file://src/github.com/mendersoftware/mender/LIC_FILES_CHKSUM.sha256;md5=69a48b331ae876b6775139310ec72f1b"
LICENSE = "Apache-2.0 & BSD-2-Clause & BSD-3-Clause & ISC & MIT & OLDAP-2.8 & OpenSSL"

DEPENDS += "xz openssl"
RDEPENDS:${PN} += "liblzma openssl"


do_compile:prepend() {
    # The go build seems to download extra things after the preliminary bitbake instgated fetch which makes
    # patching in the usual way impossible.
    rm -f ${S}/src/github.com/mendersoftware/mender/vendor/github.com/mendersoftware/openssl/fips.go
    sed -i 's/C.uchar)(chost)/C.char)(chost)/g' ${S}/src/github.com/mendersoftware/mender/vendor/github.com/mendersoftware/openssl/hostname.go
    sed -i 's/C.uchar)(cemail)/C.char)(cemail)/g' ${S}/src/github.com/mendersoftware/mender/vendor/github.com/mendersoftware/openssl/hostname.go
}
