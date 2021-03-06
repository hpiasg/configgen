package de.uni_potsdam.hpi.asg.configgen.generators;

/*
 * Copyright (C) 2017 Norman Kluge
 * 
 * This file is part of ASGconfiggen.
 * 
 * ASGconfiggen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ASGconfiggen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ASGconfiggen.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;

import de.uni_potsdam.hpi.asg.common.misc.CommonConstants;
import de.uni_potsdam.hpi.asg.configgen.ConfigGenMain;
import de.uni_potsdam.hpi.asg.configgen.Configuration;
import de.uni_potsdam.hpi.asg.configgen.Configuration.BooleanParam;
import de.uni_potsdam.hpi.asg.configgen.Configuration.EnumParam;
import de.uni_potsdam.hpi.asg.configgen.Configuration.TextParam;
import de.uni_potsdam.hpi.asg.resyntool.io.Config;
import de.uni_potsdam.hpi.asg.resyntool.io.RemoteInvocation;
import de.uni_potsdam.hpi.asg.resyntool.io.ToolConfig;

public class ResynGenerator {

    private Configuration config;
    private File          file;

    public ResynGenerator(Configuration config) {
        this.config = config;
    }

    public boolean generate() {
        Config resynconfig = new Config();
        resynconfig.componentconfig = "";
        resynconfig.workdir = "";
        if(!config.getBooleanValue(BooleanParam.defaultTechDeActivated)) {
            String techname = config.getEnumValue(EnumParam.defaultTech);
            resynconfig.defaultTech = CommonConstants.DEF_TECH_DIR_STR + File.separator + techname + CommonConstants.XMLTECH_FILE_EXTENSION;
        }
        resynconfig.toolconfig = new ToolConfig();
        resynconfig.toolconfig.desijcmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.DesiJ));
        resynconfig.toolconfig.balsaccmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.BalsaC));
        resynconfig.toolconfig.balsanetlistcmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.BalsaNetlist));
        resynconfig.toolconfig.petrifycmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.Petrify));
        resynconfig.toolconfig.petresetcmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.Petreset));
        resynconfig.toolconfig.mpsatcmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.MPSAT));
        resynconfig.toolconfig.punfcmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.PUNF));
        resynconfig.toolconfig.asglogiccmd = ConfigExportHelper.formatCmd(config.getTextValue(TextParam.ASGlogic));
        resynconfig.toolconfig.designCompilerCmd = new RemoteInvocation();
        resynconfig.toolconfig.designCompilerCmd.hostname = config.getTextValue(TextParam.Hostname);
        resynconfig.toolconfig.designCompilerCmd.username = config.getTextValue(TextParam.Username);
        resynconfig.toolconfig.designCompilerCmd.password = config.getTextValue(TextParam.Password);
        resynconfig.toolconfig.designCompilerCmd.workingdir = config.getTextValue(TextParam.WorkingDir);

        file = new File(config.getTextValue(TextParam.OutDir), ConfigGenMain.RESYN_CONFIG);
        return ConfigExportHelper.writeOut(Config.class, resynconfig, file);
    }

    public File getFile() {
        return file;
    }
}
