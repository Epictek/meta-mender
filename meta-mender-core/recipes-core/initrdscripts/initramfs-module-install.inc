FILESEXTRAPATHS:prepend:mender-grub := "${THISDIR}/files:"

SRC_URI:append:mender-grub = " file://init-install-efi-mender.sh "

do_install:append:mender-grub() {
    # Overwrite the version of this file provided by upstream
    sed -e 's#[@]MENDER_STORAGE_DEVICE[@]#${MENDER_STORAGE_DEVICE}#' ${WORKDIR}/init-install-efi-mender.sh > init-install-efi-mender-altered.sh
}
