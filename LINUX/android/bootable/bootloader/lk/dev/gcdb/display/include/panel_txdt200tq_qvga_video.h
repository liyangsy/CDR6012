/* Copyright (c) 2015, The Linux Foundation. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *  * Neither the name of The Linux Foundation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#ifndef _PANEL_TXDT200TQ_QVGA_VIDEO_H_
#define _PANEL_TXDT200TQ_QVGA_VIDEO_H_
/*---------------------------------------------------------------------------*/
/* HEADER files                                                              */
/*---------------------------------------------------------------------------*/
#include "panel.h"

/*---------------------------------------------------------------------------*/
/* Panel configuration                                                       */
/*---------------------------------------------------------------------------*/
static struct panel_config txdt200tq_qvga_video_panel_data = {
	"qcom,mdss_dsi_txdt200tq_qvga_video", "dsi:0:", "qcom,mdss-dsi-panel",
	10, 0, "DISPLAY_1", 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel resolution                                                          */
/*---------------------------------------------------------------------------*/
static struct panel_resolution txdt200tq_qvga_video_panel_res = {
	240, 320, 28, 60, 110, 0, 16, 40, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel color information                                                   */
/*---------------------------------------------------------------------------*/
static struct color_info txdt200tq_qvga_video_color = {
	16, 0, 0xff, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel on/off command information                                          */
/*---------------------------------------------------------------------------*/
static char txdt200tq_qvga_video_on_cmd0[] = {
	0x04, 0x00, 0x39, 0xC0,
	0xCF, 0x00, 0x99, 0x30
};

static char txdt200tq_qvga_video_on_cmd1[] = {
	0x05, 0x00, 0x39, 0xC0,
	0xED, 0x64, 0x03, 0x12,
	0x81, 0xFF, 0xFF, 0xFF
};

static char txdt200tq_qvga_video_on_cmd2[] = {
	0x04, 0x00, 0x39, 0xC0,
	0xE8, 0x85, 0x10, 0x7A
};

static char txdt200tq_qvga_video_on_cmd3[] = {
	0x06, 0x00, 0x39, 0xC0,
	0xCB, 0x39, 0x2C, 0x00,
	0x34, 0x02, 0xFF, 0xFF
};

static char txdt200tq_qvga_video_on_cmd4[] = {
	0x04, 0x00, 0x39, 0xC0,
	0xF6, 0x01, 0x30, 0x00
};

static char txdt200tq_qvga_video_on_cmd5[] = {
	0xF7, 0x20, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd6[] = {
	0x03, 0x00, 0x39, 0xC0,
	0xEA, 0x00, 0x00, 0xFF
};

static char txdt200tq_qvga_video_on_cmd7[] = {
	0xC0, 0x21, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd8[] = {
	0xC1, 0x12, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd9[] = {
	0x03, 0x00, 0x39, 0xC0,
	0xC5, 0x3F, 0x3C, 0xFF
};

static char txdt200tq_qvga_video_on_cmd10[] = {
	0xC7, 0xB1, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd11[] = {
	0x3A, 0x55, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd12[] = {
	0x36, 0x08, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd13[] = {
	0x03, 0x00, 0x39, 0xC0,
	0xB1, 0x00, 0x1B, 0xFF
};

static char txdt200tq_qvga_video_on_cmd14[] = {
	0x03, 0x00, 0x39, 0xC0,
	0xB4, 0x00, 0x00, 0xFF
};

static char txdt200tq_qvga_video_on_cmd15[] = {
	0x03, 0x00, 0x39, 0xC0,
	0xB6, 0x0A, 0xA2, 0xFF
};

static char txdt200tq_qvga_video_on_cmd16[] = {
	0xF2, 0x00, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd17[] = {
	0x26, 0x01, 0x15, 0x80
};

static char txdt200tq_qvga_video_on_cmd18[] = {
	0x10, 0x00, 0x39, 0xC0,
	0xE0, 0x0F, 0x1E, 0x1B,
	0x0A, 0x0B, 0x05, 0x4A,
	0x86, 0x39, 0x09, 0x13,
	0x06, 0x0A, 0x03, 0x00,
};

static char txdt200tq_qvga_video_on_cmd19[] = {
	0x10, 0x00, 0x39, 0xC0,
	0xE1, 0x00, 0x1D, 0x21,
	0x03, 0x0E, 0x04, 0x36,
	0x14, 0x48, 0x03, 0x0C,
	0x0B, 0x34, 0x38, 0x0F,
};

static char txdt200tq_qvga_video_on_cmd20[] = {
	0x11, 0x00, 0x05, 0x80
};

static char txdt200tq_qvga_video_on_cmd21[] = {
	0x29, 0x00, 0x05, 0x80
};

static struct mipi_dsi_cmd txdt200tq_qvga_video_on_command[] = {
	{0x8, txdt200tq_qvga_video_on_cmd0, 0x00},
	{0xC, txdt200tq_qvga_video_on_cmd1, 0x00},
	{0x8, txdt200tq_qvga_video_on_cmd2, 0x00},
	{0xC, txdt200tq_qvga_video_on_cmd3, 0x00},
	{0x8, txdt200tq_qvga_video_on_cmd4, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd5, 0x00},
	{0x8, txdt200tq_qvga_video_on_cmd6, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd7, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd8, 0x00},
	{0x8, txdt200tq_qvga_video_on_cmd9, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd10, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd11, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd12, 0x00},
	{0x8, txdt200tq_qvga_video_on_cmd13, 0x00},
	{0x8, txdt200tq_qvga_video_on_cmd14, 0x00},
	{0x8, txdt200tq_qvga_video_on_cmd15, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd16, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd17, 0x00},
	{0x14, txdt200tq_qvga_video_on_cmd18, 0x00},
	{0x14, txdt200tq_qvga_video_on_cmd19, 0x00},
	{0x4, txdt200tq_qvga_video_on_cmd20, 0x80},
	{0x4, txdt200tq_qvga_video_on_cmd21, 0x00}
};

#define TXDT200TQ_QVGA_VIDEO_ON_COMMAND 22


static char txdt200tq_qvga_videooff_cmd0[] = {
	0x28, 0x00, 0x05, 0x80
};

static char txdt200tq_qvga_videooff_cmd1[] = {
	0x10, 0x00, 0x05, 0x80
};

static struct mipi_dsi_cmd txdt200tq_qvga_video_off_command[] = {
	{0x4, txdt200tq_qvga_videooff_cmd0, 0x05},
	{0x4, txdt200tq_qvga_videooff_cmd1, 0x78}
};

#define TXDT200TQ_QVGA_VIDEO_OFF_COMMAND 2

static struct command_state txdt200tq_qvga_video_state = {
	0, 1
};

/*---------------------------------------------------------------------------*/
/* Command mode panel information                                            */
/*---------------------------------------------------------------------------*/
static struct commandpanel_info txdt200tq_qvga_video_command_panel = {
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Video mode panel information                                              */
/*---------------------------------------------------------------------------*/
static struct videopanel_info txdt200tq_qvga_video_video_panel = {
	0, 0, 0, 0, 1, 1, 1, 0, 0x9
};

/*---------------------------------------------------------------------------*/
/* Lane configuration                                                        */
/*---------------------------------------------------------------------------*/
static struct lane_configuration txdt200tq_qvga_video_lane_config = {
	1, 0, 1, 0, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel timing                                                              */
/*---------------------------------------------------------------------------*/
static const uint32_t txdt200tq_qvga_video_timings[] = {
	0x3C, 0x0E, 0x06, 0x00, 0x26, 0x2A, 0x0A, 0x10, 0x0E, 0x03, 0x04, 0x00
};

static const uint32_t txdt200tq_qvga_thulium_video_timings[] = {
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x18, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
};

static struct panel_timing txdt200tq_qvga_video_timing_info = {
	0x0, 0x04, 0x05, 0x11
};

/*---------------------------------------------------------------------------*/
/* Panel reset sequence                                                      */
/*---------------------------------------------------------------------------*/
static struct panel_reset_sequence txdt200tq_qvga_video_reset_seq = {
	{1, 0, 1, }, {20, 20, 50, }, 2
};

/*---------------------------------------------------------------------------*/
/* Backlight setting                                                         */
/*---------------------------------------------------------------------------*/
static struct backlight txdt200tq_qvga_video_backlight = {
	1, 1, 4095, 100, 1, "PMIC_8941"		/* BL_WLED */
};

static struct labibb_desc txdt200tq_qvga_video_labibb = {
	0, 1, 5500000, 5500000, 5500000, 5500000, 3, 3, 1, 0
};

/*---------------------------------------------------------------------------*/
/* Dynamic fps supported frequencies by panel                                */
/*---------------------------------------------------------------------------*/
static const struct dfps_panel_info txdt200tq_qvga_video_dfps = {
	1, 8, {53, 54, 55, 56, 57, 58, 59, 60}
};

#endif
