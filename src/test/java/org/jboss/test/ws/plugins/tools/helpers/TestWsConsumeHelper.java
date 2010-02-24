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
 * 
 * @author alessio.soldano@jboss.com
 * @since 24-Feb-2010
 *
 */
public class TestWsConsumeHelper implements VerifyScriptHelper, SetupScriptHelper
{
   private Long lastModificationTime = null;

   @Override
   public boolean verify(File basedir, File localRepositoryPath, Map<?, ?> context) throws Exception
   {
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
   }

}
