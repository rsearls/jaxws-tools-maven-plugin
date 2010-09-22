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
import java.net.URLClassLoader;

public class WSContractProviderParams
{
   private boolean fork;
   private String endpointClass;
   private URLClassLoader loader;
   private boolean extension;
   private boolean generateSource;
   private boolean generateWsdl;
   private File outputDirectory;
   private File resourceDirectory;
   private File sourceDirectory;
   private String argLine;

   public boolean isFork()
   {
      return fork;
   }
   public void setFork(boolean fork)
   {
      this.fork = fork;
   }
   public String getArgLine()
   {
      return argLine;
   }
   public void setArgLine(String argLine)
   {
      this.argLine = argLine;
   }
   public String getEndpointClass()
   {
      return endpointClass;
   }
   public void setEndpointClass(String endpointClass)
   {
      this.endpointClass = endpointClass;
   }
   public URLClassLoader getLoader()
   {
      return loader;
   }
   public void setLoader(URLClassLoader loader)
   {
      this.loader = loader;
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
   public boolean isGenerateWsdl()
   {
      return generateWsdl;
   }
   public void setGenerateWsdl(boolean generateWsdl)
   {
      this.generateWsdl = generateWsdl;
   }
   public File getOutputDirectory()
   {
      return outputDirectory;
   }
   public void setOutputDirectory(File outputDirectory)
   {
      this.outputDirectory = outputDirectory;
   }
   public File getResourceDirectory()
   {
      return resourceDirectory;
   }
   public void setResourceDirectory(File resourceDirectory)
   {
      this.resourceDirectory = resourceDirectory;
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
