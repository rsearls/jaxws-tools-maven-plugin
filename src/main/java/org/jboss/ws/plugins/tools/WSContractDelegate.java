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
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class WSContractDelegate
{
   public void runProvider(WSContractProviderParams params) throws Exception
   {
      ClassLoader loader = params.getLoader();
      Class<?> providerClass = loader.loadClass("org.jboss.wsf.spi.tools.WSContractProvider");
      Object provider = providerClass.getMethod("newInstance").invoke(null);
      setupProvider(providerClass, provider, params);
      Method m = providerClass.getMethod("provide", new Class<?>[]{String.class});
      m.invoke(provider, new Object[]{params.getEndpointClass()});
   }
   
   public void runConsumer(WSContractConsumerParams params, String wsdl) throws Exception
   {
      ClassLoader loader = params.getLoader();
      Class<?> consumerClass = loader.loadClass("org.jboss.wsf.spi.tools.WSContractConsumer");
      Object consumer = consumerClass.getMethod("newInstance").invoke(null);
      setupConsumer(consumerClass, consumer, params);
      Method m = consumerClass.getMethod("consume", new Class<?>[]{String.class});
      m.invoke(consumer, new Object[]{wsdl});
   }
   
   private static void setupConsumer(Class<?> consumerClass, Object consumer, WSContractConsumerParams params) throws Exception
   {
      callMethod(consumerClass, consumer, "setAdditionalCompilerClassPath", params.getAdditionalCompilerClassPath());
      Method m = consumerClass.getMethod("setMessageStream", new Class<?>[]{PrintStream.class});
      m.invoke(consumer, new Object[]{System.out});
      List<String> bindingFiles = params.getBindingFiles();
      if (bindingFiles != null && !bindingFiles.isEmpty())
      {
         List<File> files = new LinkedList<File>();
         for (String bf : bindingFiles)
         {
            files.add(new File(bf));
         }
         callMethod(consumerClass, consumer, "setBindingFiles", files);
      }
      if (params.getCatalog() != null)
      {
         callMethod(consumerClass, consumer, "setCatalog", params.getCatalog());
      }
      callMethod(consumerClass, consumer, "setExtension", params.isExtension());
      callMethod(consumerClass, consumer, "setGenerateSource", params.isGenerateSource());
      callMethod(consumerClass, consumer, "setNoCompile", params.isNoCompile());
      if (params.getOutputDirectory() != null)
      {
         callMethod(consumerClass, consumer, "setOutputDirectory", params.getOutputDirectory());
      }
      if (params.getSourceDirectory() != null)
      {
         callMethod(consumerClass, consumer, "setSourceDirectory", params.getSourceDirectory());
      }
      if (params.getTarget() != null)
      {
         callMethod(consumerClass, consumer, "setTarget", params.getTarget());
      }
      if (params.getTargetPackage() != null)
      {
         callMethod(consumerClass, consumer, "setTargetPackage", params.getTargetPackage());
      }
      if (params.getWsdlLocation() != null)
      {
         callMethod(consumerClass, consumer, "setWsdlLocation", params.getWsdlLocation());
      }
   }
   
   private static Object callMethod(Class<?> clazz, Object obj, String name, boolean param) throws Exception
   {
      Method m = clazz.getMethod(name, new Class<?>[]{boolean.class});
      return m.invoke(obj, new Object[]{param});
   }
   
   private static <T> Object callMethod(Class<?> clazz, Object obj, String name, T param) throws Exception
   {
      Method m = clazz.getMethod(name, new Class<?>[]{param.getClass()});
      return m.invoke(obj, new Object[]{param});
   }
   
   @SuppressWarnings("rawtypes")
   private static Object callMethod(Class<?> clazz, Object obj, String name, List param) throws Exception
   {
      Method m = clazz.getMethod(name, new Class<?>[]{List.class});
      return m.invoke(obj, new Object[]{param});
   }
   
   private static void setupProvider(Class<?> providerClass, Object provider, WSContractProviderParams params) throws Exception
   {
      Method m = providerClass.getMethod("setClassLoader", new Class<?>[]{ClassLoader.class});
      m.invoke(provider, new Object[]{params.getLoader()});
      callMethod(providerClass, provider, "setExtension", params.isExtension());
      callMethod(providerClass, provider, "setGenerateSource", params.isGenerateSource());
      callMethod(providerClass, provider, "setGenerateWsdl", params.isGenerateWsdl());
      Method m2 = providerClass.getMethod("setMessageStream", new Class<?>[]{PrintStream.class});
      m2.invoke(provider, new Object[]{System.out});
      if (params.getOutputDirectory() != null)
      {
         callMethod(providerClass, provider, "setOutputDirectory", params.getOutputDirectory());
      }
      if (params.getResourceDirectory() != null)
      {
         callMethod(providerClass, provider, "setResourceDirectory", params.getResourceDirectory());
      }
      if (params.getSourceDirectory() != null)
      {
         callMethod(providerClass, provider, "setSourceDirectory", params.getSourceDirectory());
      }
   }

}
