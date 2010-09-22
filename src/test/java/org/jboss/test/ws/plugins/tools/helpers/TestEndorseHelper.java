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
package org.jboss.test.ws.plugins.tools.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * A helper class for testing the plugin through bsh scripts
 * 
 * @author alessio.soldano@jboss.com
 * @since 21-Sep-2010
 *
 */
public class TestEndorseHelper implements VerifyScriptHelper, SetupScriptHelper
{
   private Long lastModificationTime = null;
   private Long lastModificationTime2 = null;
   private static final String JAXWS_22_ENDPOINT_SERVICE_CONSTRUCTOR = "public EndpointService(URL wsdlLocation, WebServiceFeature... features)";
   private static final String JAXWS_22_ENDPOINT_SERVICE_CONSTRUCTOR_CONTENTS = "super(wsdlLocation, serviceName, features);";

   @Override
   public boolean verify(File basedir, File localRepositoryPath, Map<?, ?> context) throws Exception
   {
      //fist execution checks
      File endpointServiceFile = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "wsconsume" + File.separator + "java" +
            File.separator + "foo" + File.separator + "bar" + File.separator + "EndpointService.java");
      if (!endpointServiceFile.exists())
      {
         System.out.println(endpointServiceFile + " not found!");
         return false;
      }
      if (lastModificationTime != null && endpointServiceFile.lastModified() == lastModificationTime)
      {
         System.out.println(endpointServiceFile + " was not modified by the plugin!");
         return false;
      }
      if (!readContents(endpointServiceFile).contains(JAXWS_22_ENDPOINT_SERVICE_CONSTRUCTOR_CONTENTS))
      {
         System.out.println("Could not find JAXWS 2.2 constructor '" + JAXWS_22_ENDPOINT_SERVICE_CONSTRUCTOR + "' in " + endpointServiceFile);
         return false;
      }
      
      //second execution checks
      File endpointServiceFile2 = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "wsconsume" + File.separator + "java" +
            File.separator + "foo" + File.separator + "test" + File.separator + "bar" + File.separator + "EndpointService.java");
      if (!endpointServiceFile2.exists())
      {
         System.out.println(endpointServiceFile2 + " not found!");
         return false;
      }
      if (lastModificationTime2 != null && endpointServiceFile2.lastModified() == lastModificationTime2)
      {
         System.out.println(endpointServiceFile2 + " was not modified by the plugin!");
         return false;
      }
      if (!readContents(endpointServiceFile2).contains(JAXWS_22_ENDPOINT_SERVICE_CONSTRUCTOR_CONTENTS))
      {
         System.out.println("Could not find JAXWS 2.2 constructor '" + JAXWS_22_ENDPOINT_SERVICE_CONSTRUCTOR + "' in " + endpointServiceFile2);
         return false;
      }
      
      //classes checks
      File classesFoo = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "classes" + File.separator + "foo");
      if (!classesFoo.exists())
      {
         System.out.println(classesFoo + " dir not found!");
         return false;
      }
      File classesFooBar = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "classes" + File.separator + "foo" + File.separator + "bar");
      if (!classesFooBar.exists())
      {
         System.out.println(classesFooBar + " dir not found!");
         return false;
      }
      File classesFooTest = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "test-classes" + File.separator + "foo" + File.separator + "test");
      if (!classesFooTest.exists())
      {
         System.out.println(classesFooTest + " dir not found!");
         return false;
      }
      
      return true;
   }

   @Override
   public void setup(File basedir, File localRepositoryPath, Map<?, ?> context) throws Exception
   {
      //first execution
      File endpointServiceFile = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "wsconsume" + File.separator + "java" +
            File.separator + "foo" + File.separator + "bar" + File.separator + "EndpointService.java");
      if (endpointServiceFile.exists())
      {
         this.lastModificationTime = endpointServiceFile.lastModified();
      }
      
      //second execution
      File endpointServiceFile2 = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "wsconsume" + File.separator + "java" +
            File.separator + "foo" + File.separator + "test" + File.separator + "bar" + File.separator + "EndpointService.java");
      if (endpointServiceFile2.exists())
      {
         this.lastModificationTime2 = endpointServiceFile2.lastModified();
      }
   }
   
   private String readContents(File file) throws Exception
   {
      BufferedReader in = new BufferedReader(new FileReader(file));
      StringBuffer buffer = new StringBuffer();
      String line;
      while ((line = in.readLine()) != null)
      {
         buffer.append(line);
      }
      in.close();
      return buffer.toString();
   }

}
