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
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * Generic mojo for wsconsume tool
 * 
 * @author alessio.soldano@jboss.com
 * @since 24-Feb-2010
 *
 */
public abstract class AbstractWsConsumeMojo extends AbstractToolsMojo
{
   /**
    * The list of wsdl urls / files to consume
    * 
    * @parameter
    */
   private List<String> wsdls;

   /**
    * Specifies the JAX-WS and JAXB binding files to use on import operations.
    * 
    * @parameter
    */
   protected List<String> bindingFiles;

   /**
    * Sets the OASIS XML Catalog file to use for entity resolution.
    * 
    * @parameter
    */
   private File catalog;

   /**
    * Sets the source directory. This directory will contain any generated Java source.
    * If the directory does not exist, it will be created.
    * 
    * @parameter default-value="${project.build.directory}/wsconsume/java"
    */
   protected File sourceDirectory;

   protected Boolean noCompile = true; //let Maven compile the sources

   /**
    * Sets the target package for generated source. If not specified the default
    * is based off of the XML namespace.
    * 
    * @parameter
    */
   protected String targetPackage;

   /**
    * Sets the @@WebService.wsdlLocation and @@WebServiceClient.wsdlLocation attributes to a custom value.
    * 
    * @parameter
    */
   private String wsdlLocation;

   /**
    * Set the target JAX-WS specification target. Defaults to <code>2.0</code>. Allowed values are <code>2.0</code>, <code>2.1</code>
    * 
    * @parameter
    */
   private String target;

   public void execute() throws MojoExecutionException
   {
      Log log = getLog();
      if (wsdls == null)
      {
         getLog().info("No wsdl URL / file specified, nothing to do.");
         return;
      }
      
      if (verbose)
      {
         log.info("Classpath:");
         for (String s : getClasspathElements())
         {
            log.info(" " + s);
         }
      }
      
      ClassLoader origLoader = Thread.currentThread().getContextClassLoader();
      try
      {
         URLClassLoader loader = getMavenClasspathAwareClassLoader();
         Thread.currentThread().setContextClassLoader(loader);
         
         WSContractConsumerParams params = new WSContractConsumerParams();
         params.setAdditionalCompilerClassPath(new LinkedList<String>(getClasspathElements()));
         params.setBindingFiles(bindingFiles);
         params.setCatalog(catalog);
         params.setExtension(extension);
         params.setGenerateSource(generateSource);
         params.setLoader(loader);
         params.setNoCompile(noCompile);
         params.setOutputDirectory(getOutputDirectory());
         params.setSourceDirectory(sourceDirectory);
         params.setTarget(target);
         params.setTargetPackage(targetPackage);
         params.setWsdlLocation(wsdlLocation);
         params.setArgLine(argLine);
         params.setFork(fork);
         WSContractDelegate delegate = new WSContractDelegate();
         
         for (String wsdl : wsdls)
         {
            try
            {
               delegate.runConsumer(params, wsdl);
            }
            catch (MalformedURLException mue)
            {
               log.error("Skipping invalid wsdl reference: " + wsdl);
            }
            catch (Exception e)
            {
               throw new MojoExecutionException("Error while running wsconsume", e);
            }
         }
         updateProjectSourceRoots();
      }
      finally
      {
         Thread.currentThread().setContextClassLoader(origLoader);
      }
   }

   public List<String> getWsdls()
   {
      return wsdls;
   }

   public List<String> getBindingFiles()
   {
      return bindingFiles;
   }

   public File getCatalog()
   {
      return catalog;
   }

   public File getSourceDirectory()
   {
      return sourceDirectory;
   }

   public String getTargetPackage()
   {
      return targetPackage;
   }

   public String getWsdlLocation()
   {
      return wsdlLocation;
   }

   public String getTarget()
   {
      return target;
   }
}
