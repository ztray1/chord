package kmaru.jchord;

public class Key implements Comparable {

	String identifier;

	byte[] key;

	public Key(byte[] key) {
		this.key = key;
	}

	public Key(String identifier) {
		this.identifier = identifier;
		this.key = Hash.hash(identifier);
	}

	public boolean isBetween(Key fromKey, Key toKey) {
		if (fromKey.compareTo(toKey) < 0) {
			if (this.compareTo(fromKey) > 0 && this.compareTo(toKey) < 0) {
				return true;
			}
		} else if (fromKey.compareTo(toKey) > 0) {
			if (this.compareTo(toKey) < 0 || this.compareTo(fromKey) > 0) {
				return true;
			}
		}
		return false;
	}

	public Key createStartKey(int index) {
		byte[] newKey = new byte[key.length];
		System.arraycopy(key, 0, newKey, 0, key.length);
		int carry = 0;
		for (int i = (Hash.KEY_LENGTH - 1) / 8; i >= 0; i--) {
			int value = key[i] & 0xff;
			value += (1 << (index % 8)) + carry;
			newKey[i] = (byte) value;
			if (value <= 0xff) {
				break;
			}
			carry = (value >> 8) & 0xff;
		}
		return new Key(newKey);
	}

	public Key createindexKey(int index) {
		byte[] newKey = new byte[key.length];
		System.arraycopy(key, 0, newKey, 0, key.length);
		int carry = 0;
		for (int i = (Hash.KEY_LENGTH - 1) / 8 - (index - 1) / 8; i >= 0; i--) {
			int value = key[i] & 0xff;
			if (carry != 0) { // if there is a carry, this digit will add 1 and
								// exit.
				newKey[i] = (byte) (value + 1);
				break;
			}
			if (index % 8 != 0 || index == 0)
				value += (1 << (index % 8)) + carry;
			else
				value += 1 << 8 + carry;
			newKey[i] = (byte) value;
			if (value <= 0xff) {
				break;
			}
			carry = (value >> 8) & 0xff;
		}
		return new Key(newKey);
	}

	public int compareTo(Object obj) {
		Key targetKey = (Key) obj;
		for (int i = 0; i < key.length; i++) {
			int loperand = (this.key[i] & 0xff);
			int roperand = (targetKey.getKey()[i] & 0xff);
			if (loperand != roperand) {
				return (loperand - roperand);
			}
		}
		return 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (key.length > 4) {
			for (int i = 0; i < key.length; i++) {
				sb.append(Integer.toString(((int) key[i]) & 0xff) + ".");
			}
		} else {
			long n = 0;
			for (int i = key.length - 1, j = 0; i >= 0; i--, j++) {
				n |= ((key[i] << (8 * j)) & (0xffL << (8 * j)));
			}
			sb.append(Long.toString(n));
		}
		return sb.substring(0, sb.length() - 1).toString();
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

}
