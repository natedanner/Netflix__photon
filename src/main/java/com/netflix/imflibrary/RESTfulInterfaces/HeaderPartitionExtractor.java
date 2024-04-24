package com.netflix.imflibrary.RESTfulInterfaces;

import com.netflix.imflibrary.exceptions.IMFException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * A thin stateless class that assists in providing the byte offsets for the HeaderPartition within an IMF Track file
 */
public final class HeaderPartitionExtractor {

    /**
     * To prevent instantiation
     */
    private HeaderPartitionExtractor() {
    }

    /**
     * A stateless method that will return the size of the RandomIndexPack present within a MXF file. In a typical IMF workflow
     * this would be the first method that would need to be invoked to perform IMF essence component level validation
     * @param essenceFooter4Bytes - the last 4 bytes of the MXF file used to infer the size of the RandomIndexPack
     * @return a long integer value representing the size of the RandomIndexPack
     */
    public static Long getRandomIndexPackSize(PayloadRecord essenceFooter4Bytes){
        return IMPValidator.getRandomIndexPackSize(essenceFooter4Bytes);
    }

    /**
     * A stateless method that will read and parse the RandomIndexPack within a MXF file and return the byte offsets
     * corresponding to the Header partition of the MXF file. In a typical IMF workflow this would be the second method after
     * {@link #getRandomIndexPackSize(PayloadRecord)} that would need to be invoked to perform IMF essence component
     * level validation
     * @param randomIndexPackPayload - a payload containing the raw bytes corresponding to the RandomIndexPack of the MXF file
     * @param randomIndexPackSize - size of the RandomIndexPack of the MXF file
     * @return list of long integer values representing the byte offsets of the partitions in the MXF file
     * @throws IOException - any I/O related error is exposed through an IOException
     */
    public static List<Long> getHeaderPartitionByteOffsets(PayloadRecord randomIndexPackPayload, Long randomIndexPackSize) throws IOException{
        List<Long> partitionByteOffsets = IMPValidator.getEssencePartitionOffsets(randomIndexPackPayload, randomIndexPackSize);
        return partitionByteOffsets.subList(0, 2);
    }
}
