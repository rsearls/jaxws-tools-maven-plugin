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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;

/**
 * Utility for dealing with URLs
 *
 * @author rsearls
 * @since 06-Feb-2015
 */
public class UrlUtils
{
   private static final BitSet UNRESERVED = new BitSet(Byte.MAX_VALUE - Byte.MIN_VALUE + 1);

   private static final int RADIX = 16;

   private static final int MASK = 0xf;

   private UrlUtils()
   {
   }

   private static final String ENCODING = "UTF-8";

   static
   {
      try
      {
         byte[] bytes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'():/".getBytes(ENCODING);
         for (byte aByte : bytes)
         {
            UNRESERVED.set(aByte);
         }
      }
      catch (UnsupportedEncodingException e)
      {
         // can't happen as UTF-8 must be present
      }
   }

   public static URL getURL(File file) throws MalformedURLException
   {
      URL url = new URL(file.toURI().toASCIIString());
      // encode any characters that do not comply with RFC 2396
      // this is primarily to handle Windows where the user's home directory contains spaces
      try
      {
         byte[] bytes = url.toString().getBytes(ENCODING);
         StringBuilder buf = new StringBuilder(bytes.length);
         for (byte b : bytes)
         {
            if (b > 0 && UNRESERVED.get(b))
            {
               buf.append((char) b);
            }
            else
            {
               buf.append('%');
               buf.append(Character.forDigit(b >>> 4 & MASK, RADIX));
               buf.append(Character.forDigit(b & MASK, RADIX));
            }
         }
         return new URL(buf.toString());
      }
      catch (UnsupportedEncodingException e)
      {
         // should not happen as UTF-8 must be present
         throw new RuntimeException(e);
      }
   }
}
