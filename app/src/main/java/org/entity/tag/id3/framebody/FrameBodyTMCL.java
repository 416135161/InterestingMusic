/**
 *  @author : Paul Taylor
 *  @author : Eric Farng
 *
 *  Version @version:$Id$
 *
 *  MusicTag Copyright (C)2003,2004
 *
 *  This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 *  or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 *  you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * Description:
 *
 */
package org.entity.tag.id3.framebody;

import org.entity.tag.InvalidTagException;
import org.entity.tag.id3.ID3v24Frames;

import java.nio.ByteBuffer;


public class FrameBodyTMCL extends AbstractFrameBodyTextInfo implements ID3v24FrameBody
{
    /**
     * Creates a new FrameBodyTMCL datatype.
     */
    public FrameBodyTMCL()
    {
    }

    public FrameBodyTMCL(FrameBodyTMCL body)
    {
        super(body);
    }

    /**
     * Creates a new FrameBodyTMCL datatype.
     *
     * @param textEncoding
     * @param text
     */
    public FrameBodyTMCL(byte textEncoding, String text)
    {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTMCL datatype.
     *
     * @param byteBuffer
     * @param frameSize
     * @throws InvalidTagException
     */
    public FrameBodyTMCL(ByteBuffer byteBuffer, int frameSize) throws InvalidTagException
    {
        super(byteBuffer, frameSize);
    }

    /**
     * The ID3v2 frame identifier
     *
     * @return the ID3v2 frame identifier  for this frame type
     */
    public String getIdentifier()
    {
        return ID3v24Frames.FRAME_ID_MUSICIAN_CREDITS;
    }
}