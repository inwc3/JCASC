package com.hiveworkshop.blizzard.casc.vfs;

import com.hiveworkshop.blizzard.casc.Key;

/**
 * A reference to part of a file in CASC storage.
 */
public class StorageReference {
	/**
	 * Logical offset of this chunk.
	 */
	private long offset = 0;

	/**
	 * Logical size of this chunk.
	 */
	private long size = 0;

	/**
	 * Encoding key of chunk.
	 */
	private Key encodingKey = null;

	/**
	 * Physical size of stored data banks.
	 */
	private long physicalSize = 0;

	/**
	 * Member that purpose is currently not know. Known values are 0x00 and 0x0A.
	 */
	private byte unknownMember1 = 0;

	/**
	 * Logical size of data banks.
	 */
	private long actualSize = 0;

	public StorageReference(final long offset, final long size, final Key encodingKey, final int physicalSize,
			final byte unknownMember1, final int actualSize) {
		this.offset = offset;
		this.size = size;
		this.encodingKey = encodingKey;
		this.physicalSize = physicalSize;
		this.unknownMember1 = unknownMember1;
		this.actualSize = actualSize;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("FileReference{encodingKey=");
		builder.append(encodingKey);
		builder.append(", offset=");
		builder.append(offset);
		builder.append(", size=");
		builder.append(size);
		builder.append(", physicalSize=");
		builder.append(physicalSize);
		builder.append(", unknownMember1=");
		builder.append(unknownMember1);
		builder.append(", actualSize=");
		builder.append(actualSize);
		builder.append("}");
		return builder.toString();
	}

	public long getOffset() {
		return offset;
	}

	public long getSize() {
		return size;
	}

	public Key getEncodingKey() {
		return encodingKey;
	}

	public long getPhysicalSize() {
		return physicalSize;
	}

	public byte getUnknownMember1() {
		return unknownMember1;
	}

	public long getActualSize() {
		return actualSize;
	}

}
