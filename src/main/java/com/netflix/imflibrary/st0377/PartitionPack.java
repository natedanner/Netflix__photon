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

import com.netflix.imflibrary.IMFErrorLogger;
import com.netflix.imflibrary.KLVPacket;
import com.netflix.imflibrary.st0377.header.UL;
import com.netflix.imflibrary.utils.ByteProvider;
import com.netflix.imflibrary.exceptions.MXFException;
import com.netflix.imflibrary.annotations.MXFProperty;
import com.netflix.imflibrary.MXFPropertyPopulator;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Object model corresponding to a PartitionPack defined in st377-1:2011
 */
@Immutable
@SuppressWarnings({"PMD.FinalFieldCouldBeStatic"})
public final class  PartitionPack
{
    private static final String ERROR_DESCRIPTION_PREFIX = "MXF Header Partition: " + PartitionPack.class.getSimpleName() + " : ";
    private static final byte[] KEY      = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x00, 0x0d, 0x01, 0x02, 0x01, 0x01, 0x00, 0x00, 0x00};
    private static final byte[] KEY_MASK = {   1,    1,     1,    1,    1,    1,   1,    0,    1,    1,    1,     1,    1,   0,    0,    1};
    private static final Long UNKNOWN_BYTE_OFFSET = -1L;
    private static final byte GENERIC_STREAM_PARTITION_PACK_KEY_PARTITION_STATUS = 0x11;

    private final KLVPacket.Header header;

    @MXFProperty(size=2) private final Integer majorVersion = null;
    @MXFProperty(size=2) private final Integer minorVersion = null;
    @MXFProperty(size=4) private final Long kagSize = null;
    @MXFProperty(size=8) private final Long thisPartition = null;
    @MXFProperty(size=8) private final Long previousPartition = null;
    @MXFProperty(size=8) private final Long footerPartition = null;
    @MXFProperty(size=8) private final Long headerByteCount = null;
    @MXFProperty(size=8) private final Long indexByteCount = null;
    @MXFProperty(size=4) private final Long indexSID = null;
    @MXFProperty(size=8) private final Long bodyOffset = null;
    @MXFProperty(size=4) private final Long bodySID = null;
    @MXFProperty(size=16) private final byte[] operationalPattern = null;

    private final CompoundDataTypes.MXFCollections.MXFCollection<UL> essenceContainerBatch;

    private final KLVPacket.Header nextHeader;

    private final PartitionPackType partitionPackType;

    /**
     * An enum to represent the PartitionPackTypes which can be extended only by the PartitionPack class
     */
    public enum PartitionPackType {
        /*
        Enum corresponding to the HeaderPartitionPack, based on the KeyValue as defined in SMPTE ST-0377-1:2011
         */
        HeaderPartitionPack(0x02, "HeaderPartitionPack"),

        /*
        Enum corresponding to the BodyPartitionPack, based on the KeyValue as defined in SMPTE ST-0377-1:2011
         */
        BodyPartitionPack(0x03, "BodyPartitionPack"),

        /*
        Enum corresponding to the FooterPartitionPack, based on the KeyValue as defined in SMPTE ST-0377-1:2011
         */
        FooterPartitionPack(0x04, "FooterPartitionPack");

        private final Integer partitionTypeKey;
        private final String partitionTypeString;

        //To prevent other objects from constructing new PartitionTypes
        private PartitionPackType(Integer partitionTypeKey, String partitionTypeString){
            this.partitionTypeKey = partitionTypeKey;
            this.partitionTypeString = partitionTypeString;
        }

        /**
         * Accessor for the PartitionTypeString
         * @return string representing this partition type
         *
         */
        public String getPartitionTypeString(){
            return this.partitionTypeString;
        }

        /**
         * Accessor for the PartitionTypeKey
         * @return key corresponding to this Partition Pack
         */
        public Integer getPartitionPackTypeKey(){
            return this.partitionTypeKey;
        }

        /**
         * Given a key this method returns the corresponding PartitionPackType
         * @param partitionTypeKey the key corresponding to this Partition Pack
         * @return a PartitionPackType corresponding to the PartitionTypeKey that was passed in.
         * @throws MXFException if an invalid PartitionTypeKey was passed in.
         */
        public static PartitionPackType getPartitionPackTypeKey(Integer partitionTypeKey) throws MXFException{
            if(partitionTypeKey.equals(HeaderPartitionPack.getPartitionPackTypeKey())){
                return HeaderPartitionPack;
            }
            else if(partitionTypeKey.equals(BodyPartitionPack.getPartitionPackTypeKey())){
                return BodyPartitionPack;
            }
            else if(partitionTypeKey .equals(FooterPartitionPack.getPartitionPackTypeKey())){
                return FooterPartitionPack;
            }
            else{
                throw new MXFException(String.format("Unrecognized partition pack type"));
            }
        }
    }

    /**
     * Instantiates a new Partition pack.
     *
     * @param byteProvider the mxf byte provider
     * @throws IOException the iO exception
     */
    public PartitionPack(ByteProvider byteProvider) throws IOException
    {
        this(byteProvider, UNKNOWN_BYTE_OFFSET, false);
    }

    /**
     * Getter for the operational pattern that this MXF file complies to
     *
     * @return the byte [ ]
     */
    public byte[] getOperationalPattern()
    {
        return Arrays.copyOf(this.operationalPattern, this.operationalPattern.length);
    }

    /**
     * Getter for the number of essence container ULs that are referred by this partition pack
     * @return the number of essence container ULs that are referred by this partition pack
     */
    public int getNumberOfEssenceContainerULs()
    {
        return this.essenceContainerBatch.size();
    }

    /**
     * Instantiates a new Partition pack.
     *
     * @param byteProvider the mxf byte provider
     * @param byteOffset the byteOffset from the HeaderPartition of this partition pack
     * @param checkForSucceedingKLVFillItem the check for succeeding kLV fill item
     * @throws IOException - any I/O related error will be exposed through an IOException
     */
    public PartitionPack(ByteProvider byteProvider, Long byteOffset, boolean checkForSucceedingKLVFillItem) throws IOException
    {
        this(byteProvider, byteOffset, checkForSucceedingKLVFillItem, null);
    }

    /**
     * Instantiates a new Partition pack.
     *
     * @param byteProvider the mxf byte provider
     * @param byteOffset the byteOffset from the HeaderPartition of this partition pack
     * @param checkForSucceedingKLVFillItem the check for succeeding kLV fill item
     * @param imfErrorLogger the imf error logger
     * @throws IOException - any I/O related error will be exposed through an IOException
     */
    public PartitionPack(ByteProvider byteProvider, Long byteOffset, boolean checkForSucceedingKLVFillItem, @Nullable IMFErrorLogger imfErrorLogger)
            throws IOException
    {
        this.header = new KLVPacket.Header(byteProvider, byteOffset);
        validateHeaderKey();

        this.partitionPackType = PartitionPackType.getPartitionPackTypeKey(this.header.getSetOrPackKindKey());

        MXFPropertyPopulator.populateField(byteProvider, this, "major_version");
        MXFPropertyPopulator.populateField(byteProvider, this, "minor_version");
        MXFPropertyPopulator.populateField(byteProvider, this, "KAG_size");

        MXFPropertyPopulator.populateField(byteProvider, this, "this_partition");
        if (this.thisPartition < 0)
        {
            String errorMessage = String.format("Value of this_partition = %d(0x%x) which is outside the supported range 0-0x%x",
                    this.thisPartition, this.thisPartition, Long.MAX_VALUE);
            handleError(imfErrorLogger, errorMessage);
        }

        MXFPropertyPopulator.populateField(byteProvider, this, "previous_partition");
        if (this.previousPartition < 0)
        {
            String errorMessage = String.format("Value of previous_partition = %d(0x%x) which is outside the supported range 0-0x%x",
                    this.previousPartition, this.previousPartition, Long.MAX_VALUE);
            handleError(imfErrorLogger, errorMessage);
        }

        MXFPropertyPopulator.populateField(byteProvider, this, "footer_partition");
        if (this.footerPartition < 0)
        {
            String errorMessage = String.format("Value of footer_partition = %d(0x%x) which is outside the supported range 0-0x%x",
                    this.footerPartition, this.footerPartition, Long.MAX_VALUE);
            handleError(imfErrorLogger, errorMessage);
        }

        MXFPropertyPopulator.populateField(byteProvider, this, "header_byte_count");
        if (this.headerByteCount < 0)
        {
            String errorMessage = String.format("Value of header_byte_count = %d(0x%x) which is outside the supported range 0-0x%x",
                    this.headerByteCount, this.headerByteCount, Long.MAX_VALUE);
            handleError(imfErrorLogger, errorMessage);
        }

        MXFPropertyPopulator.populateField(byteProvider, this, "index_byte_count");
        if (this.indexByteCount < 0)
        {
            String errorMessage = String.format("Value of index_byte_count = %d(0x%x) which is outside the supported range 0-0x%x",
                    this.indexByteCount, this.indexByteCount, Long.MAX_VALUE);
            handleError(imfErrorLogger, errorMessage);
        }

        MXFPropertyPopulator.populateField(byteProvider, this, "index_SID");

        MXFPropertyPopulator.populateField(byteProvider, this, "body_offset");
        if (this.bodyOffset < 0)
        {
            String errorMessage = String.format("Value of body_offset = %d(0x%x) which is outside the supported range 0-0x%x",
                    this.bodyOffset, this.bodyOffset, Long.MAX_VALUE);
            handleError(imfErrorLogger, errorMessage);
        }

        MXFPropertyPopulator.populateField(byteProvider, this, "body_SID");
        MXFPropertyPopulator.populateField(byteProvider, this, "operational_pattern");

        CompoundDataTypes.MXFCollections.Header cHeader = new CompoundDataTypes.MXFCollections.Header(byteProvider);
        List<UL> cList = new ArrayList<>();

        if ((cHeader.getNumberOfElements() != 0) && (cHeader.getSizeOfElement() != KLVPacket.KEY_FIELD_SIZE))
        {
            throw new MXFException(String.format("Element size = %d in EssenceContainerBatch header is different from expected size = %d",
                    cHeader.getSizeOfElement(), KLVPacket.KEY_FIELD_SIZE));
        }

        for (long i=0; i<cHeader.getNumberOfElements(); i++)
        {
            cList.add(new UL(byteProvider.getBytes(KLVPacket.KEY_FIELD_SIZE)));
        }

        this.essenceContainerBatch = new CompoundDataTypes.MXFCollections.MXFCollection<>(cHeader, cList, "EssenceContainerBatch");

        if (checkForSucceedingKLVFillItem)
        {
            //Offset of the next KLV packet would be the offset of the current KLV packet + KLV size
            this.nextHeader = new KLVPacket.Header(byteProvider, byteOffset+this.header.getKLSize()+this.header.getVSize());
        }
        else
        {
            this.nextHeader = null;
        }

    }

    private void validateHeaderKey()
    {
        for (int i=0; i< KLVPacket.KEY_FIELD_SIZE; i++)
        {
            if( (PartitionPack.KEY_MASK[i] != 0) && (PartitionPack.KEY[i] != this.header.getKey()[i]) )
            {
                throw new MXFException(String.format("Partition Pack key value = 0x%x at position (zero-indexed) = %d, is different from expected value = 0x%x",
                        this.header.getKey()[i], i, PartitionPack.KEY[i]));
            }
        }
    }

    private void handleError(IMFErrorLogger imfErrorLogger, String errorMessage)
    {
        if (imfErrorLogger == null)
        {
            throw new MXFException(errorMessage);
        }
        else
        {
            imfErrorLogger.addError(IMFErrorLogger.IMFErrors.ErrorCodes.IMF_ESSENCE_METADATA_ERROR, IMFErrorLogger.IMFErrors.ErrorLevels.NON_FATAL,
                    PartitionPack.ERROR_DESCRIPTION_PREFIX + errorMessage);
        }
    }

    /**
     * Partition pack type of this pack.
     *
     * @return PartitionPackType
     */
    public PartitionPackType getPartitionPackType()
    {
        return this.partitionPackType;
    }

    /**
     * Checks if this is a header partition.
     *
     * @return the boolean
     */
    public boolean isHeaderPartition()
    {
        return this.partitionPackType == PartitionPackType.HeaderPartitionPack;
    }

    /**
     * Checks if this is a valid header partition.
     *
     * @return the boolean
     */
    public boolean isValidHeaderPartition()
    {
        return isHeaderPartition() && (this.thisPartition == 0) && (this.previousPartition == 0) && (this.headerByteCount != 0);
    }

    /**
     * Checks if this is a body partition.
     *
     * @return the boolean
     */
    public boolean isBodyPartition()
    {
        return this.partitionPackType == PartitionPackType.BodyPartitionPack;
    }

    /**
     * Checks of this is a footer partition.
     *
     * @return the boolean
     */
    public boolean isFooterPartition()
    {
        return this.partitionPackType == PartitionPackType.FooterPartitionPack;
    }

    /**
     * Checks if this is a valid footer partition.
     *
     * @return the boolean
     */
    public boolean isValidFooterPartition()
    {
        return (this.footerPartition.equals(this.thisPartition)) && (this.bodyOffset == 0) && (this.bodySID == 0);
    }

    /**
     * Checks if this is a Generic Stream partition.
     *
     * @return the boolean
     */
    public boolean isGenericStreamPartition()
    {
        return this.isBodyPartition() && (this.header.getKey()[14] == PartitionPack.GENERIC_STREAM_PARTITION_PACK_KEY_PARTITION_STATUS);
    }

    /**
     * Getter for the header byte count that represents the count of bytes used for HeaderMetadata and Primer pack.
     *
     * @return the header byte count
     */
    public long getHeaderByteCount()
    {
        return this.headerByteCount;
    }

    /**
     * Checks if this partition pack has header metadata.
     *
     * @return the boolean
     */
    public boolean hasHeaderMetadata()
    {
        return this.headerByteCount != 0;
    }

    /**
     * Getter for count of bytes used for IndexTable segments.
     *
     * @return the index byte count
     */
    public long getIndexByteCount()
    {
        return this.indexByteCount;
    }

    /**
     * Getter for the index table segment identifier in this partition.
     *
     * @return the index sID
     */
    public long getIndexSID()
    {
        return this.indexSID;
    }

    /**
     * Checks if this partition has index table segments.
     *
     * @return the boolean
     */
    public boolean hasIndexTableSegments()
    {
        return (this.indexByteCount != 0) && (this.indexSID != 0);
    }

    /**
     * Getter for identifier of the Essence container segment found in this partition.
     *
     * @return the body sID
     */
    public long getBodySID()
    {
        return this.bodySID;
    }

    /**
     * Checks if this partition has essence container data.
     *
     * @return the boolean
     */
    public boolean hasEssenceContainer()
    {
        return this.bodySID != 0;
    }

    /**
     * Getter for the KLV packet size.
     *
     * @return the KLV packet size
     */
    public long getKLVPacketSize()
    {
        return KLVPacket.KEY_FIELD_SIZE + this.header.getLSize() + this.header.getVSize();
    }

    /**
     * Getter for the size of the value field of a KLV packet.
     *
     * @return the value size
     */
    public long getVSize()
    {
        return this.header.getVSize();
    }

    /**
     * Getter for the size field of a KLV packet.
     *
     * @return the size
     */
    public long getSize()
    {
        return this.header.getKLSize() + this.header.getVSize();
    }

    /**
     * Getter for this partition's byte offset.
     *
     * @return the partition byte offset
     */
    public long getPartitionByteOffset()
    {
        return this.thisPartition;
    }

    /**
     * Getter for this partition's previous_partition byte offset.
     *
     * @return the previous_partition byte offset
     */
    public long getPreviousPartitionByteOffset(){
        return this.previousPartition;
    }

    /**
     * Gets essence stream segment start stream position.
     *
     * @return byte offset of the start of the essence segment, relative to the start of the essence stream
     * @throws MXFException if the partition does not contain essence
     */
    public long getEssenceStreamSegmentStartStreamPosition() throws MXFException
    {
        if (!this.hasEssenceContainer())
        {
            throw new MXFException("This partition does not contain essence data");
        }
        return this.bodyOffset;
    }

    /**
     * Checks if the next header was read.
     *
     * @return the boolean
     */
    public boolean nextHeaderWasRead()
    {
        return this.nextHeader != null;
    }

    /**
     * Checks if the next packet is a KLV fill item.
     *
     * @return the boolean
     */
    public boolean nextPacketIsKLVFillItem()
    {
        return this.nextHeaderWasRead() &&
                KLVPacket.isKLVFillItem(Arrays.copyOf(this.nextHeader.getKey(), this.nextHeader.getKey().length));
    }

    /**
     * Getter for the partition data byte offset.
     *
     * @return the partition data byte offset
     */
    public long getPartitionDataByteOffset()
    {
        if (this.nextPacketIsKLVFillItem())
        {
            return this.thisPartition + this.getSize() + this.nextHeader.getKLSize() + this.nextHeader.getVSize();
        }
        else
        {
            return this.thisPartition + this.getSize();
        }
    }

    /**
     * A method that returns a string representation of a PartitionPack object
     *
     * @return string representing the object
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("================== PartitionPack ======================\n");
        sb.append(this.header.toString());
        sb.append(String.format("major_version = %d%n", this.majorVersion));
        sb.append(String.format("minor_version = %d%n", this.minorVersion));
        sb.append(String.format("KAG_size = %d%n", this.kagSize));
        sb.append(String.format("this_partition = 0x%x%n", this.thisPartition));
        sb.append(String.format("previous_partition = 0x%x%n", this.previousPartition));
        sb.append(String.format("footer_partition = 0x%x%n", this.footerPartition));
        sb.append(String.format("header_byte_count = 0x%x%n", this.headerByteCount));
        sb.append(String.format("index_byte_count = 0x%x%n", this.indexByteCount));
        sb.append(String.format("index_SID = %d%n", this.indexSID));
        sb.append(String.format("body_offset = 0x%x%n", this.bodyOffset));
        sb.append(String.format("body_SID = %d%n", this.bodySID));
        sb.append(String.format("operational_pattern = 0x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%n",
                this.operationalPattern[0], this.operationalPattern[1], this.operationalPattern[2], this.operationalPattern[3],
                this.operationalPattern[4], this.operationalPattern[5], this.operationalPattern[6], this.operationalPattern[7],
                this.operationalPattern[8], this.operationalPattern[9], this.operationalPattern[10], this.operationalPattern[11],
                this.operationalPattern[12], this.operationalPattern[13], this.operationalPattern[14], this.operationalPattern[15]));
        sb.append(this.essenceContainerBatch.toString());

        return sb.toString();
    }

    private enum PartitionKind
    {
        /**
         * The Header.
         */
        Header(0x02),
        /**
         * The Body.
         */
        Body(0x03),
        /**
         * The Footer.
         */
        Footer(0x04);

        private final int value;

        private PartitionKind(int value)
        {
            this.value = value;
        }

        /**
         * Gets value.
         *
         * @return the value
         */
        public int getValue()
        {
            return this.value;
        }
    }


}
