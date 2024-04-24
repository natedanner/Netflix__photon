/*
 *
 * Copyright 2015 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.netflix.imflibrary.st0377;

import com.netflix.imflibrary.utils.ByteProvider;
import com.netflix.imflibrary.exceptions.MXFException;
import com.netflix.imflibrary.annotations.MXFProperty;
import com.netflix.imflibrary.MXFPropertyPopulator;
import com.netflix.imflibrary.KLVPacket;

import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object model corresponding to an IndexTable segment as defined in st377-1:2011
 */
@Immutable
@SuppressWarnings({"PMD.FinalFieldCouldBeStatic"})
public final class IndexTableSegment
{
    private static final byte[] KEY      = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x53, 0x01, 0x01, 0x0d, 0x01, 0x02, 0x01, 0x01, 0x10, 0x01, 0x00};
    private static final byte[] KEY_MASK = {   1,    1,    1,    1,    1,    0,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1};
    private static final Map<Integer, String> LOCAL_TAG_TO_ITEM_NAME;
    static
    {
        Map<Integer, String> localTagToItemName = new HashMap<>();
        localTagToItemName.put(0x3c0a, "instance_UID");
        localTagToItemName.put(0x3f0b, "indexEditRate");
        localTagToItemName.put(0x3f0c, "index_start_position");
        localTagToItemName.put(0x3f0d, "index_duration");
        localTagToItemName.put(0x3f05, "edit_unit_byte_count");
        localTagToItemName.put(0x3f06, "index_SID");
        localTagToItemName.put(0x3f07, "body_SID");
        localTagToItemName.put(0x3f08, "slice_count");
        localTagToItemName.put(0x3f0e, "pos_table_count");
        localTagToItemName.put(0x3f0f, "ext_start_offset");
        localTagToItemName.put(0x3f10, "vbe_byte_count");
        LOCAL_TAG_TO_ITEM_NAME = Collections.unmodifiableMap(localTagToItemName);
    }

    private final KLVPacket.Header header;
    @MXFProperty(size=16) private final byte[] instanceUID = null;
    @MXFProperty(size=0) private final CompoundDataTypes.Rational indexEditRate = null;
    @MXFProperty(size=8) private final Long indexStartPosition = null;
    @MXFProperty(size=8) private final Long indexDuration = null;
    @MXFProperty(size=4) private final Long editUnitByteCount = null;
    @MXFProperty(size=4) private final Long indexSID = null;
    @MXFProperty(size=4) private final Long bodySID = null;
    @MXFProperty(size=1) private final Short sliceCount = null;
    @MXFProperty(size=1) private final Short posTableCount = null;
    private final IndexEntryArray indexEntryArray;
    @MXFProperty(size=8) private final Long extStartOffset = null;
    @MXFProperty(size=8) private final Long vbeByteCount = null;


    /**
     * Instantiates a new Index table segment.
     *
     * @param byteProvider the mxf byte provider
     * @param header the header
     * @throws IOException the iO exception
     */
    public IndexTableSegment(ByteProvider byteProvider, KLVPacket.Header header) throws IOException
    {

        if (!IndexTableSegment.isValidKey(header.getKey()))
        {
            throw new MXFException(String.format("IndexTableSegment key = %s invalid", Arrays.toString(header.getKey())));
        }

        this.header = header;
        if ((this.header.getKey()[5] != 0x53) && (this.header.getKey()[5] != 0x13))
        {
            throw new MXFException(String.format("Found index table segment with registry designator byte value = 0x%x, only 0x53h or 0x13h are supported presently",
                    this.header.getKey()[5]));
        }

        long numBytesToRead = this.header.getVSize();
        long numBytesRead = 0;

        IndexEntryArray indexEntryArray = null;
        while (numBytesRead < numBytesToRead)
        {
            Integer itemTag = MXFPropertyPopulator.getUnsignedShortAsInt(byteProvider.getBytes(2), KLVPacket.BYTE_ORDER);
            numBytesRead += 2;

            long itemSize;
            if (this.header.getKey()[5] == 0x53)
            {
                itemSize = MXFPropertyPopulator.getUnsignedShortAsInt(byteProvider.getBytes(2), KLVPacket.BYTE_ORDER);
                numBytesRead += 2;
            }
            else
            {//(this.header.getKey()[5] == 0x13)
                KLVPacket.LengthField lengthField = KLVPacket.getLength(byteProvider);
                itemSize = lengthField.value;
                numBytesRead += lengthField.sizeOfLengthField;
            }

            String itemName = IndexTableSegment.LOCAL_TAG_TO_ITEM_NAME.get(itemTag);
            if (itemName != null)
            {
                int expectedLength = MXFPropertyPopulator.getFieldSizeInBytes(this, itemName);
                if((expectedLength > 0) && (itemSize != expectedLength))
                {
                    throw new MXFException(String.format("Actual length from bitstream = %d is different from expected length = %d",
                            itemSize, expectedLength));
                }
                MXFPropertyPopulator.populateField(byteProvider, this, itemName);
                numBytesRead += itemSize;
            }
            else if (itemTag == 0x3f0a)
            {
                indexEntryArray = new IndexEntryArray(byteProvider);
                numBytesRead += itemSize;
            }
            else
            {
                byteProvider.skipBytes(itemSize);
                numBytesRead += itemSize;
            }
        }
        this.indexEntryArray = indexEntryArray;

    }

    /**
     * A method that returns a string representation of an Index table segment object
     *
     * @return string representing the object
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("================== IndexTableSegment ======================\n");
        sb.append(this.header.toString());
        sb.append(String.format("instance_UID = 0x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%n",
                this.instanceUID[0], this.instanceUID[1], this.instanceUID[2], this.instanceUID[3],
                this.instanceUID[4], this.instanceUID[5], this.instanceUID[6], this.instanceUID[7],
                this.instanceUID[8], this.instanceUID[9], this.instanceUID[10], this.instanceUID[11],
                this.instanceUID[12], this.instanceUID[13], this.instanceUID[14], this.instanceUID[15]));
        if (this.indexEditRate != null)
        {
            sb.append("================== IndexEditRate ======================\n");
            sb.append(this.indexEditRate.toString());
        }
        sb.append(String.format("index_start_position = 0x%x%n", this.indexStartPosition));
        sb.append(String.format("index_duration = 0x%x%n", this.indexDuration));
        sb.append(String.format("edit_unit_byte_count = %d%n", this.editUnitByteCount));
        sb.append(String.format("index_SID = %d%n", this.indexSID));
        sb.append(String.format("body_SID = %d%n", this.bodySID));
        sb.append(String.format("slice_count = %d%n", this.sliceCount));
        sb.append(String.format("pos_table_count = %d%n", this.posTableCount));
        if (this.indexEntryArray != null)
        {
            sb.append(this.indexEntryArray.toString());
        }
        sb.append(String.format("ext_start_offset = 0x%x%n", this.extStartOffset));
        sb.append(String.format("vbe_byte_count = %d%n", this.vbeByteCount));
        return sb.toString();
    }

    /**
     * Getter for the index entries.
     *
     * @return a read-only copy of a list of IndexTableSegment.IndexEntryArray.IndexEntry or null when not present
     */
    public List<IndexEntryArray.IndexEntry> getIndexEntries()
    {
        if (this.indexEntryArray != null)
        {
            return Collections.unmodifiableList(this.indexEntryArray.indexEntries);
        }
        else
        {
            return null;
        }
    }

    public CompoundDataTypes.Rational getIndexEditRate() {
        return indexEditRate;
    }

    /**
     * Checks if the key passed in corresponds to a IndexTable segment
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean isValidKey(byte[] key)
    {
        for (int i=0; i< KLVPacket.KEY_FIELD_SIZE; i++)
        {
            if((IndexTableSegment.KEY_MASK[i] != 0) && (IndexTableSegment.KEY[i] != key[i]))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Object model corresponding to a collection of Index Table entries
     */
    @Immutable
    public static final class IndexEntryArray
    {
        private final CompoundDataTypes.MXFCollections.Header header;
        private final List<IndexEntry> indexEntries = new ArrayList<>();

        /**
         * Instantiates a new Index entry array.
         *
         * @param byteProvider the mxf byte provider
         * @throws IOException the iO exception
         */
        IndexEntryArray(ByteProvider byteProvider) throws IOException
        {
            this.header = new CompoundDataTypes.MXFCollections.Header(byteProvider);
            for (long i=0; i<header.getNumberOfElements(); i++)
            {
                indexEntries.add(new IndexEntry(byteProvider));
                byteProvider.skipBytes(this.header.getSizeOfElement() - 11);
            }
        }

        /**
         * A method that returns a string representation of an IndexEntryArray object
         *
         * @return string representing the object
         */
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append("================== IndexEntryArray ======================\n");
            sb.append(this.header.toString());
            for (IndexEntry indexEntry : indexEntries)
            {
                sb.append(indexEntry.toString());
            }
            return sb.toString();
        }

        /**
         * Object model corresponding to an Index table entry
         */
        @Immutable
        public static final class IndexEntry
        {
            @MXFProperty(size=1) private final Byte temporal_offset = null;
            @MXFProperty(size=1) private final Byte key_frame_offset = null;
            @MXFProperty(size=1) private final Byte flags = null;
            @MXFProperty(size=8) private final Long stream_offset = null;

            /**
             * Instantiates a new Index entry.
             *
             * @param byteProvider the mxf byte provider
             * @throws IOException the iO exception
             */
            IndexEntry(ByteProvider byteProvider) throws IOException
            {
                MXFPropertyPopulator.populateField(byteProvider, this, "temporal_offset");
                MXFPropertyPopulator.populateField(byteProvider, this, "key_frame_offset");
                MXFPropertyPopulator.populateField(byteProvider, this, "flags");
                MXFPropertyPopulator.populateField(byteProvider, this, "stream_offset");
            }

            /**
             * Gets stream offset.
             *
             * @return the stream offset
             */
            public long getStreamOffset()
            {
                return this.stream_offset;
            }

            /**
             * A method that returns a string representation of an IndexEntry object
             *
             * @return string representing the object
             */
            public String toString()
            {
                return String.format("temporal_offset = %d, key_frame_offset = %d, flags = 0x%x, stream_offset = 0x%x%n",
                        this.temporal_offset, this.key_frame_offset, this.flags, this.stream_offset);
            }

        }
    }

}
