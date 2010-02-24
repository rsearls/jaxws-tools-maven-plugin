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
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.jboss.wsf.spi.tools.WSContractConsumer;

/**
 * 
 * 
 * @author alessio.soldano@jboss.com
 * @since 24-Feb-2010
 *
 */
abstract class AbstractWsConsumeMojo extends AbstractToolsMojo
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
   protected List<File> bindingFiles;

   /**
    * Sets the OASIS XML Catalog file to use for entity resolution.
    * 
    * @parameter
    */
   private File catalog;

   /**
    * Sets the source directory. This directory will contain any generated Java source.
    * If the directory does not exist, it will be created. If not specified,
    * the output directory will be used instead.
    * 
    * @parameter default-value="${project.build.directory}/wsconsume/java"
    */
   protected File sourceDirectory;

   /**
    * Enables/Disables SOAP 1.2 binding extension
    * 
    * @parameter default-value="false"
    */
   protected Boolean extension;

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
         for (String s : getClasspath())
         {
            log.info(" " + s);
         }
      }
      
      ClassLoader origLoader = Thread.currentThread().getContextClassLoader();
      try
      {
         Thread.currentThread().setContextClassLoader(getMavenClasspathAwareClassLoader(origLoader));
         WSContractConsumer consumer = WSContractConsumer.newInstance();
         setupConsumer(consumer);
         for (String wsdl : wsdls)
         {
            try
            {
               consumer.consume(wsdl);
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
         project.addCompileSourceRoot(sourceDirectory.getAbsolutePath());
      }
      finally
      {
         Thread.currentThread().setContextClassLoader(origLoader);
      }
   }

   private void setupConsumer(WSContractConsumer consumer)
   {
      consumer.setAdditionalCompilerClassPath(new LinkedList<String>(getClasspath()));
      consumer.setMessageStream(System.out);
      if (bindingFiles != null && !bindingFiles.isEmpty())
      {
         consumer.setBindingFiles(bindingFiles);
      }
      if (catalog != null)
      {
         consumer.setCatalog(catalog);
      }
      consumer.setExtension(extension);
      consumer.setGenerateSource(generateSource);
      consumer.setNoCompile(noCompile);
      File outputDir = getDestDir();
      if (outputDir != null)
      {
         consumer.setOutputDirectory(outputDir);
      }
      if (sourceDirectory != null)
      {
         consumer.setSourceDirectory(sourceDirectory);
      }
      if (target != null)
      {
         consumer.setTarget(target);
      }
      if (targetPackage != null)
      {
         consumer.setTargetPackage(targetPackage);
      }
      if (wsdlLocation != null)
      {
         consumer.setWsdlLocation(wsdlLocation);
      }
   }
}
