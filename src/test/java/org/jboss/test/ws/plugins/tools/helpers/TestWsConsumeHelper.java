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

import java.io.File;
import java.util.Map;

/**
 * A helper class for testing the plugin through bsh scripts
 * 
 * @author alessio.soldano@jboss.com
 * @since 24-Feb-2010
 *
 */
public class TestWsConsumeHelper implements VerifyScriptHelper, SetupScriptHelper
{
   private Long lastModificationTime = null;
   private Long lastModificationTime2 = null;

   @Override
   public boolean verify(File basedir, File localRepositoryPath, Map<?, ?> context) throws Exception
   {
      //fist execution checks
      File endpointFile = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "wsconsume" + File.separator + "java" +
            File.separator + "foo" + File.separator + "bar" + File.separator + "Endpoint.java");
      if (!endpointFile.exists())
      {
         System.out.println(endpointFile + " not found!");
         return false;
      }
      if (lastModificationTime != null && endpointFile.lastModified() == lastModificationTime)
      {
         System.out.println(endpointFile + " was not modified by the plugin!");
         return false;
      }
      
      //second execution checks
      File endpointFile2 = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "generated" + File.separator + "java-sources" +
            File.separator + "foo" + File.separator + "bar2" + File.separator + "Endpoint.java");
      if (!endpointFile2.exists())
      {
         System.out.println(endpointFile2 + " not found!");
         return false;
      }
      if (lastModificationTime2 != null && endpointFile2.lastModified() == lastModificationTime2)
      {
         System.out.println(endpointFile2 + " was not modified by the plugin!");
         return false;
      }
      
      //classes checks
      File classesOrg = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "classes" + File.separator + "org");
      File classesFoo = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "classes" + File.separator + "foo");
      if (!classesFoo.exists() || !classesOrg.exists())
      {
         return false;
      }
      File testClassesOrg = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "test-classes" + File.separator + "org");
      File testClassesFoo = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "test-classes" + File.separator + "foo");
      if (!testClassesFoo.exists() || !testClassesOrg.exists())
      {
         return false;
      }
      
      return true;
   }

   @Override
   public void setup(File basedir, File localRepositoryPath, Map<?, ?> context) throws Exception
   {
      File endpointFile = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "wsconsume" + File.separator + "java" +
            File.separator + "foo" + File.separator + "bar" + File.separator + "Endpoint.java");
      if (endpointFile.exists())
      {
         this.lastModificationTime = endpointFile.lastModified();
      }
      
      File endpointFile2 = new File(basedir.getAbsolutePath() + File.separator + "target" + File.separator + "generated" + File.separator + "java-sources" +
            File.separator + "foo" + File.separator + "bar2" + File.separator + "Endpoint.java");
      if (endpointFile2.exists())
      {
         this.lastModificationTime2 = endpointFile2.lastModified();
      }
   }

}
