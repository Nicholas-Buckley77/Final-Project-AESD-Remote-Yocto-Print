# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "The snappy web interface for your 3D printer"
HOMEPAGE = "https://octoprint.org"
# NOTE: License in setup.py/PKGINFO is: GNU Affero General Public License v3
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   THIRDPARTYLICENSES.md
#   src/octoprint/templates/dialogs/about/license.jinja2
# NOTE: Original package / source metadata indicates license is: AGPL-3.0-only
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.


# https://github.com/jin-eld/klipper-linux/tree/master/yocto inspired by


LICENSE = "AGPL-3.0-only & Unknown"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=73f1eb20517c55bf9493b7dd6e480788 \
                    file://THIRDPARTYLICENSES.md;md5=decd3df7ae4a31e1a4e946e511774adc \
                    file://src/octoprint/templates/dialogs/about/license.jinja2;md5=55e274b509037878079c75b5567d2a2a"

SRC_URI = "git://git@github.com/Nicholas-Buckley77/OctoPrint.git;protocol=ssh;branch=master \
            file://config.yaml \
            file://octoprint.run \  
    "

# Modify these as desired
PV = "0.post8835+gdfe2893+git"
SRCREV = "dfe289304943b9622605edb49e245a3f22d9ceae"

S = "${WORKDIR}/git"


inherit setuptools3 runit-service


export BUILD_SYS
export HOST_SYS
export STAGING_INCDIR
export STAGING_LIBDIR

BBCLASSEXTEND = "native"

RUNIT_SERVICES = "octoprint"


do_install:append(){
    install -d ${D}${sysconfdir}/octoprint
    install -m 0644 ${WORKDIR}/config.yaml ${D}${sysconfdir}/octoprint/config.yaml
    install -m 0755 -d ${D}${sysconfdir}/runit/octoprint
    install -m 0755 ${WORKDIR}/octoprint.run ${D}${sysconfdir}/runit/octoprint/run
    install -d ${D}${localstatedir}/lib/octoprint
}



FILES:${PN} += "${sysconfdir} ${localstatedir}"
CONFFILES:${PN} += "${sysconfdir}/octoprint/config.yaml"

DEPENDS = "${PYTHON_PN} ${PYTHON_PN}-markdown-native"

INSANE_SKIP:${PN} += "build-deps"

RDEPENDS:${PN} = "\
    ${PYTHON_PN}-markdown \
    ${PYTHON_PN}-flask \
    ${PYTHON_PN}-jinja2 \
    ${PYTHON_PN}-tornado \
    ${PYTHON_PN}-regex \
    ${PYTHON_PN}-flask-login \
    ${PYTHON_PN}-flask-babel \
    ${PYTHON_PN}-blinker \
    ${PYTHON_PN}-werkzeug \ 
    ${PYTHON_PN}-pyyaml \
    ${PYTHON_PN}-markdown \
    ${PYTHON_PN}-pyserial \
    ${PYTHON_PN}-netaddr \
    ${PYTHON_PN}-watchdog \
    ${PYTHON_PN}-netifaces \
    ${PYTHON_PN}-rsa \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-semantic-version \
    ${PYTHON_PN}-psutil \
    ${PYTHON_PN}-click \
    ${PYTHON_PN}-future \
    ${PYTHON_PN}-websocket-client \
    ${PYTHON_PN}-wrapt \
    ${PYTHON_PN}-sentry-sdk \
    ${PYTHON_PN}-monotonic \
    ${PYTHON_PN}-pip \
    "

BBCLASSEXTEND = "native"

# WARNING: the following rdepends are determined through basic analysis of the
# python sources, and might not be 100% accurate.