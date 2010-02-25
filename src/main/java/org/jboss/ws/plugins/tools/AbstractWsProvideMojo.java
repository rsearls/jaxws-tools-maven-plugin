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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.jboss.wsf.spi.tools.WSContractProvider;

/**
 * 
 * 
 * @author alessio.soldano@jboss.com
 * @since 25-Feb-2010
 *
 */
abstract class AbstractWsProvideMojo extends AbstractToolsMojo 
{
   /**
    * Enables/Disables WSDL generation.
    * 
    * @parameter default-value="false"
    */
   private boolean generateWsdl;
   
   /**
    * Sets the resource directory. This directory will contain any generated
    * WSDL and XSD files. If the directory does not exist, it will be created.
    * 
    * @parameter default-value="${project.build.directory}/wsprovide/resources"
    */
   private File resourceDirectory;
   
   /**
    * Sets the source directory. This directory will contain any generated Java source.
    * If the directory does not exist, it will be created.
    * 
    * @parameter default-value="${project.build.directory}/wsprovide/java"
    */
   protected File sourceDirectory;
   
   /**
    * The endpoint implementation class name.
    * 
    * @parameter 
    * @required
    */
   private String endpointClass;
   
   public void execute() throws MojoExecutionException
   {
      Log log = getLog();
      if (endpointClass == null)
      {
         getLog().info("No service endpoint implementation class specified, nothing to do.");
         return;
      }
      
      if (verbose)
      {
         log.info("Classpath:");
         for (String s : getClasspath())
         {
            log.info(" " + s);
         }
      }
      
      ClassLoader origLoader = Thread.currentThread().getContextClassLoader();
      ClassLoader loader = getMavenClasspathAwareClassLoader(origLoader);
      Thread.currentThread().setContextClassLoader(loader);
      try
      {
         WSContractProvider provider = WSContractProvider.newInstance();
         setupProvider(provider, loader);
         provider.provide(endpointClass);
         project.addCompileSourceRoot(sourceDirectory.getAbsolutePath());
      }
      catch (Exception e)
      {
         throw new MojoExecutionException("Error while running wsconsume", e);
      }
      finally
      {
         Thread.currentThread().setContextClassLoader(origLoader);
      }
   }
   
   private void setupProvider(WSContractProvider provider, ClassLoader loader)
   {
      provider.setClassLoader(loader);
      provider.setExtension(extension);
      provider.setGenerateSource(generateSource);
      provider.setGenerateWsdl(generateWsdl);
      provider.setMessageStream(System.out);
      File outputDir = getDestDir();
      if (outputDir != null)
      {
         provider.setOutputDirectory(outputDir);
      }
      if (resourceDirectory != null)
      {
         provider.setResourceDirectory(resourceDirectory);
      }
      if (sourceDirectory != null)
      {
         provider.setSourceDirectory(sourceDirectory);
      }
   }
}
