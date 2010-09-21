/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.ws.plugins.tools;

import java.io.File;
import java.util.List;

public class WSContractConsumerParams
{
   private boolean fork;
   private ClassLoader loader;
   private List<String> additionalCompilerClassPath;
   private List<String> bindingFiles;
   private File catalog;
   private boolean extension;
   private boolean generateSource;
   private boolean noCompile;
   private File outputDirectory;
   private File sourceDirectory;
   private String target;
   private String targetPackage;
   private String wsdlLocation;
   private String argLine;

   public String getArgLine()
   {
      return argLine;
   }
   public void setArgLine(String argLine)
   {
      this.argLine = argLine;
   }
   public void setFork(boolean fork)
   {
      this.fork = fork;
   }
   public boolean isFork()
   {
      return fork;
   }
   public void setLoader(ClassLoader loader)
   {
      this.loader = loader;
   }
   public ClassLoader getLoader()
   {
      return loader;
   }
   public List<String> getAdditionalCompilerClassPath()
   {
      return additionalCompilerClassPath;
   }
   public void setAdditionalCompilerClassPath(List<String> additionalCompilerClassPath)
   {
      this.additionalCompilerClassPath = additionalCompilerClassPath;
   }
   public List<String> getBindingFiles()
   {
      return bindingFiles;
   }
   public void setBindingFiles(List<String> bindingFiles)
   {
      this.bindingFiles = bindingFiles;
   }
   public File getCatalog()
   {
      return catalog;
   }
   public void setCatalog(File catalog)
   {
      this.catalog = catalog;
   }
   public boolean isNoCompile()
   {
      return noCompile;
   }
   public void setNoCompile(boolean noCompile)
   {
      this.noCompile = noCompile;
   }
   public String getTarget()
   {
      return target;
   }
   public void setTarget(String target)
   {
      this.target = target;
   }
   public String getTargetPackage()
   {
      return targetPackage;
   }
   public void setTargetPackage(String targetPackage)
   {
      this.targetPackage = targetPackage;
   }
   public String getWsdlLocation()
   {
      return wsdlLocation;
   }
   public void setWsdlLocation(String wsdlLocation)
   {
      this.wsdlLocation = wsdlLocation;
   }
   public boolean isExtension()
   {
      return extension;
   }
   public boolean isGenerateSource()
   {
      return generateSource;
   }
   public void setGenerateSource(boolean generateSource)
   {
      this.generateSource = generateSource;
   }
   public void setExtension(boolean extension)
   {
      this.extension = extension;
   }
   public File getOutputDirectory()
   {
      return outputDirectory;
   }
   public void setOutputDirectory(File outputDirectory)
   {
      this.outputDirectory = outputDirectory;
   }
   public File getSourceDirectory()
   {
      return sourceDirectory;
   }
   public void setSourceDirectory(File sourceDirectory)
   {
      this.sourceDirectory = sourceDirectory;
   }
}
