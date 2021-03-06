package com.hiveworkshop.blizzard.casc.vfs;

import com.hiveworkshop.blizzard.casc.Key;

/**
 * A reference to a file in CASC storage.
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
	 * Physical size of stored data.
	 */
	private long physicalSize = 0;

	/**
	 * Total size of all decompressed data banks.
	 */
	private long actualSize = 0;

	public StorageReference(final long offset, final long size, final Key encodingKey, final int physicalSize,
			final int actualSize) {
		this.offset = offset;
		this.size = size;
		this.encodingKey = encodingKey;
		this.physicalSize = physicalSize;
		this.actualSize = actualSize;
	}

	@Override
	public String toString() {

    String builder = "FileReference{encodingKey=" +
      encodingKey +
      ", offset=" +
      offset +
      ", size=" +
      size +
      ", physicalSize=" +
      physicalSize +
      ", actualSize=" +
      actualSize +
      "}";
    return builder;
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

	public long getActualSize() {
		return actualSize;
	}

}
