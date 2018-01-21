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

#ifndef _PANEL_COM40H4M42_WVGA_VIDEO_H_
#define _PANEL_COM40H4M42_WVGA_VIDEO_H_
/*---------------------------------------------------------------------------*/
/* HEADER files                                                              */
/*---------------------------------------------------------------------------*/
#include "panel.h"

/*---------------------------------------------------------------------------*/
/* Panel configuration                                                       */
/*---------------------------------------------------------------------------*/
static struct panel_config com40h4m42_wvga_video_panel_data = {
	"qcom,mdss_dsi_com40h4m42_wvga_video", "dsi:0:", "qcom,mdss-dsi-panel",
	10, 0, "DISPLAY_1", 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel resolution                                                          */
/*---------------------------------------------------------------------------*/
static struct panel_resolution com40h4m42_wvga_video_panel_res = {
	480, 800, 16, 16, 16, 0, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel color information                                                   */
/*---------------------------------------------------------------------------*/
static struct color_info com40h4m42_wvga_video_color = {
	24, 0, 0xff, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel on/off command information                                          */
/*---------------------------------------------------------------------------*/
static char com40h4m42_wvga_video_on_cmd0[] = {
	0x11, 0x00, 0x05, 0x80
};

static char com40h4m42_wvga_video_on_cmd1[] = {
	0x04, 0x00, 0x39, 0xC0,
	0xB9, 0xFF, 0x83, 0x63
};

static char com40h4m42_wvga_video_on_cmd2[] = {
	0x0E, 0x00, 0x39, 0xC0,
	0xBA, 0x80, 0x00, 0x10,
	0x08, 0x08, 0x10, 0x7E,
	0x6E, 0x6D, 0x0A, 0x01,
	0x84, 0x43, 0xFF, 0xFF
};

static char com40h4m42_wvga_video_on_cmd3[] = {
	0x36, 0x00, 0x15, 0x80
};

static char com40h4m42_wvga_video_on_cmd4[] = {
	0x3A, 0x70, 0x15, 0x80
};

static char com40h4m42_wvga_video_on_cmd5[] = {
	0x0D, 0x00, 0x39, 0xC0,
	0xB1, 0x78, 0x24, 0x06,
	0x02, 0x02, 0x03, 0x10,
	0x10, 0x34, 0x3C, 0x3F,
	0x3F, 0xFF, 0xFF, 0xFF
};

static char com40h4m42_wvga_video_on_cmd6[] = {
	0x0A, 0x00, 0x39, 0xC0,
	0xB4, 0x00, 0x08, 0x6E,
	0x07, 0x01, 0x01, 0x62,
	0x01, 0x57, 0xFF, 0xFF
};

static char com40h4m42_wvga_video_on_cmd7[] = {
	0xCC, 0x07, 0x15, 0x80
};

static char com40h4m42_wvga_video_on_cmd8[] = {
	0x1F, 0x00, 0x39, 0xC0,
	0xE0, 0x01, 0x48, 0x4D,
	0x4E, 0x58, 0xF6, 0x0B,
	0x4E, 0x12, 0xD5, 0x15,
	0x95, 0x55, 0x8E, 0x11,
	0x01, 0x48, 0x4D, 0x55,
	0x5F, 0xFD, 0x0A, 0x4E,
	0x51, 0xD3, 0x17, 0x95,
	0x96, 0x4E, 0x11, 0xFF
};

static char com40h4m42_wvga_video_on_cmd9[] = {
	0x29, 0x00, 0x05, 0x80
};

static struct mipi_dsi_cmd com40h4m42_wvga_video_on_command[] = {
	{0x4, com40h4m42_wvga_video_on_cmd0, 0x78},
	{0x8, com40h4m42_wvga_video_on_cmd1, 0x00},
	{0x14, com40h4m42_wvga_video_on_cmd2, 0x00},
	{0x4, com40h4m42_wvga_video_on_cmd3, 0x00},
	{0x4, com40h4m42_wvga_video_on_cmd4, 0x0A},
	{0x14, com40h4m42_wvga_video_on_cmd5, 0x00},
	{0x10, com40h4m42_wvga_video_on_cmd6, 0x00},
	{0x4, com40h4m42_wvga_video_on_cmd7, 0x00},
	{0x24, com40h4m42_wvga_video_on_cmd8, 0x05},
	{0x4, com40h4m42_wvga_video_on_cmd9, 0x00}
};

#define COM40H4M42_WVGA_VIDEO_ON_COMMAND 10


static char com40h4m42_wvga_videooff_cmd0[] = {
	0x28, 0x00, 0x05, 0x80
};

static char com40h4m42_wvga_videooff_cmd1[] = {
	0x10, 0x00, 0x05, 0x80
};

static struct mipi_dsi_cmd com40h4m42_wvga_video_off_command[] = {
	{0x4, com40h4m42_wvga_videooff_cmd0, 0x05},
	{0x4, com40h4m42_wvga_videooff_cmd1, 0x78}
};

#define COM40H4M42_WVGA_VIDEO_OFF_COMMAND 2

static struct command_state com40h4m42_wvga_video_state = {
	0, 1
};

/*---------------------------------------------------------------------------*/
/* Command mode panel information                                            */
/*---------------------------------------------------------------------------*/
static struct commandpanel_info com40h4m42_wvga_video_command_panel = {
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Video mode panel information                                              */
/*---------------------------------------------------------------------------*/
static struct videopanel_info com40h4m42_wvga_video_video_panel = {
	0, 0, 0, 0, 1, 1, 1, 0, 0x9
};

/*---------------------------------------------------------------------------*/
/* Lane configuration                                                        */
/*---------------------------------------------------------------------------*/
static struct lane_configuration com40h4m42_wvga_video_lane_config = {
	2, 0, 1, 1, 0, 0, 0
};

/*---------------------------------------------------------------------------*/
/* Panel timing                                                              */
/*---------------------------------------------------------------------------*/
static const uint32_t com40h4m42_wvga_video_timings[] = {
	0x66, 0x12, 0x0C, 0x00, 0x34, 0x38, 0x10, 0x16, 0x1E, 0x03, 0x04, 0x00
};

static const uint32_t com40h4m42_wvga_thulium_video_timings[] = {
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x1e, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
	0x23, 0x18, 0x7, 0x8, 0x5, 0x3, 0x4, 0xa0,
};

static struct panel_timing com40h4m42_wvga_video_timing_info = {
	0x0, 0x04, 0x05, 0x18
};

/*---------------------------------------------------------------------------*/
/* Panel reset sequence                                                      */
/*---------------------------------------------------------------------------*/
static struct panel_reset_sequence com40h4m42_wvga_video_reset_seq = {
	{1, 0, 1, }, {20, 20, 50, }, 2
};

/*---------------------------------------------------------------------------*/
/* Backlight setting                                                         */
/*---------------------------------------------------------------------------*/
static struct backlight com40h4m42_wvga_video_backlight = {
	1, 1, 4095, 100, 1, "PMIC_8941"		/* BL_WLED */
};

static struct labibb_desc com40h4m42_wvga_video_labibb = {
	0, 1, 5500000, 5500000, 5500000, 5500000, 3, 3, 1, 0
};

/*---------------------------------------------------------------------------*/
/* Dynamic fps supported frequencies by panel                                */
/*---------------------------------------------------------------------------*/
static const struct dfps_panel_info com40h4m42_wvga_video_dfps = {
	1, 8, {53, 54, 55, 56, 57, 58, 59, 60}
};

#endif
