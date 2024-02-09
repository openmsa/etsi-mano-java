/**
 *     Copyright (C) 2019-2023 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dns-api.proto

package com.ubiqube.etsi.mano.dns.api.v1;

/**
 * Protobuf type {@code com.ubiqube.etsi.mano.dns.api.AddHostResponse}
 */
public  final class AddHostResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.ubiqube.etsi.mano.dns.api.AddHostResponse)
    AddHostResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AddHostResponse.newBuilder() to construct.
  private AddHostResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AddHostResponse() {
    hostName_ = "";
    in_ = "";
    recordType_ = "";
    value_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AddHostResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private AddHostResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            id_ = input.readInt32();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            hostName_ = s;
            break;
          }
          case 24: {

            ttl_ = input.readInt32();
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            in_ = s;
            break;
          }
          case 42: {
            java.lang.String s = input.readStringRequireUtf8();

            recordType_ = s;
            break;
          }
          case 50: {
            java.lang.String s = input.readStringRequireUtf8();

            value_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.ubiqube.etsi.mano.dns.api.v1.DnsProto.internal_static_com_ubiqube_etsi_mano_dns_api_AddHostResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.ubiqube.etsi.mano.dns.api.v1.DnsProto.internal_static_com_ubiqube_etsi_mano_dns_api_AddHostResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse.class, com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse.Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private int id_;
  /**
   * <code>int32 id = 1;</code>
   * @return The id.
   */
  public int getId() {
    return id_;
  }

  public static final int HOSTNAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object hostName_;
  /**
   * <code>string hostName = 2;</code>
   * @return The hostName.
   */
  public java.lang.String getHostName() {
    java.lang.Object ref = hostName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      hostName_ = s;
      return s;
    }
  }
  /**
   * <code>string hostName = 2;</code>
   * @return The bytes for hostName.
   */
  public com.google.protobuf.ByteString
      getHostNameBytes() {
    java.lang.Object ref = hostName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      hostName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TTL_FIELD_NUMBER = 3;
  private int ttl_;
  /**
   * <code>int32 ttl = 3;</code>
   * @return The ttl.
   */
  public int getTtl() {
    return ttl_;
  }

  public static final int IN_FIELD_NUMBER = 4;
  private volatile java.lang.Object in_;
  /**
   * <code>string in = 4;</code>
   * @return The in.
   */
  public java.lang.String getIn() {
    java.lang.Object ref = in_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      in_ = s;
      return s;
    }
  }
  /**
   * <code>string in = 4;</code>
   * @return The bytes for in.
   */
  public com.google.protobuf.ByteString
      getInBytes() {
    java.lang.Object ref = in_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      in_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RECORDTYPE_FIELD_NUMBER = 5;
  private volatile java.lang.Object recordType_;
  /**
   * <code>string recordType = 5;</code>
   * @return The recordType.
   */
  public java.lang.String getRecordType() {
    java.lang.Object ref = recordType_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      recordType_ = s;
      return s;
    }
  }
  /**
   * <code>string recordType = 5;</code>
   * @return The bytes for recordType.
   */
  public com.google.protobuf.ByteString
      getRecordTypeBytes() {
    java.lang.Object ref = recordType_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      recordType_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int VALUE_FIELD_NUMBER = 6;
  private volatile java.lang.Object value_;
  /**
   * <code>string value = 6;</code>
   * @return The value.
   */
  public java.lang.String getValue() {
    java.lang.Object ref = value_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      value_ = s;
      return s;
    }
  }
  /**
   * <code>string value = 6;</code>
   * @return The bytes for value.
   */
  public com.google.protobuf.ByteString
      getValueBytes() {
    java.lang.Object ref = value_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      value_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (id_ != 0) {
      output.writeInt32(1, id_);
    }
    if (!getHostNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, hostName_);
    }
    if (ttl_ != 0) {
      output.writeInt32(3, ttl_);
    }
    if (!getInBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, in_);
    }
    if (!getRecordTypeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, recordType_);
    }
    if (!getValueBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, value_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (id_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, id_);
    }
    if (!getHostNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, hostName_);
    }
    if (ttl_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, ttl_);
    }
    if (!getInBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, in_);
    }
    if (!getRecordTypeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, recordType_);
    }
    if (!getValueBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, value_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse)) {
      return super.equals(obj);
    }
    com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse other = (com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse) obj;

    if (getId()
        != other.getId()) return false;
    if (!getHostName()
        .equals(other.getHostName())) return false;
    if (getTtl()
        != other.getTtl()) return false;
    if (!getIn()
        .equals(other.getIn())) return false;
    if (!getRecordType()
        .equals(other.getRecordType())) return false;
    if (!getValue()
        .equals(other.getValue())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + getId();
    hash = (37 * hash) + HOSTNAME_FIELD_NUMBER;
    hash = (53 * hash) + getHostName().hashCode();
    hash = (37 * hash) + TTL_FIELD_NUMBER;
    hash = (53 * hash) + getTtl();
    hash = (37 * hash) + IN_FIELD_NUMBER;
    hash = (53 * hash) + getIn().hashCode();
    hash = (37 * hash) + RECORDTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getRecordType().hashCode();
    hash = (37 * hash) + VALUE_FIELD_NUMBER;
    hash = (53 * hash) + getValue().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.ubiqube.etsi.mano.dns.api.AddHostResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.ubiqube.etsi.mano.dns.api.AddHostResponse)
      com.ubiqube.etsi.mano.dns.api.v1.AddHostResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.ubiqube.etsi.mano.dns.api.v1.DnsProto.internal_static_com_ubiqube_etsi_mano_dns_api_AddHostResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.ubiqube.etsi.mano.dns.api.v1.DnsProto.internal_static_com_ubiqube_etsi_mano_dns_api_AddHostResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse.class, com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse.Builder.class);
    }

    // Construct using com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      id_ = 0;

      hostName_ = "";

      ttl_ = 0;

      in_ = "";

      recordType_ = "";

      value_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.ubiqube.etsi.mano.dns.api.v1.DnsProto.internal_static_com_ubiqube_etsi_mano_dns_api_AddHostResponse_descriptor;
    }

    @java.lang.Override
    public com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse getDefaultInstanceForType() {
      return com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse build() {
      com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse buildPartial() {
      com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse result = new com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse(this);
      result.id_ = id_;
      result.hostName_ = hostName_;
      result.ttl_ = ttl_;
      result.in_ = in_;
      result.recordType_ = recordType_;
      result.value_ = value_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse) {
        return mergeFrom((com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse other) {
      if (other == com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse.getDefaultInstance()) return this;
      if (other.getId() != 0) {
        setId(other.getId());
      }
      if (!other.getHostName().isEmpty()) {
        hostName_ = other.hostName_;
        onChanged();
      }
      if (other.getTtl() != 0) {
        setTtl(other.getTtl());
      }
      if (!other.getIn().isEmpty()) {
        in_ = other.in_;
        onChanged();
      }
      if (!other.getRecordType().isEmpty()) {
        recordType_ = other.recordType_;
        onChanged();
      }
      if (!other.getValue().isEmpty()) {
        value_ = other.value_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int id_ ;
    /**
     * <code>int32 id = 1;</code>
     * @return The id.
     */
    public int getId() {
      return id_;
    }
    /**
     * <code>int32 id = 1;</code>
     * @param value The id to set.
     * @return This builder for chaining.
     */
    public Builder setId(int value) {
      
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearId() {
      
      id_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object hostName_ = "";
    /**
     * <code>string hostName = 2;</code>
     * @return The hostName.
     */
    public java.lang.String getHostName() {
      java.lang.Object ref = hostName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        hostName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string hostName = 2;</code>
     * @return The bytes for hostName.
     */
    public com.google.protobuf.ByteString
        getHostNameBytes() {
      java.lang.Object ref = hostName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        hostName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string hostName = 2;</code>
     * @param value The hostName to set.
     * @return This builder for chaining.
     */
    public Builder setHostName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      hostName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string hostName = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearHostName() {
      
      hostName_ = getDefaultInstance().getHostName();
      onChanged();
      return this;
    }
    /**
     * <code>string hostName = 2;</code>
     * @param value The bytes for hostName to set.
     * @return This builder for chaining.
     */
    public Builder setHostNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      hostName_ = value;
      onChanged();
      return this;
    }

    private int ttl_ ;
    /**
     * <code>int32 ttl = 3;</code>
     * @return The ttl.
     */
    public int getTtl() {
      return ttl_;
    }
    /**
     * <code>int32 ttl = 3;</code>
     * @param value The ttl to set.
     * @return This builder for chaining.
     */
    public Builder setTtl(int value) {
      
      ttl_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 ttl = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTtl() {
      
      ttl_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object in_ = "";
    /**
     * <code>string in = 4;</code>
     * @return The in.
     */
    public java.lang.String getIn() {
      java.lang.Object ref = in_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        in_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string in = 4;</code>
     * @return The bytes for in.
     */
    public com.google.protobuf.ByteString
        getInBytes() {
      java.lang.Object ref = in_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        in_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string in = 4;</code>
     * @param value The in to set.
     * @return This builder for chaining.
     */
    public Builder setIn(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      in_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string in = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearIn() {
      
      in_ = getDefaultInstance().getIn();
      onChanged();
      return this;
    }
    /**
     * <code>string in = 4;</code>
     * @param value The bytes for in to set.
     * @return This builder for chaining.
     */
    public Builder setInBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      in_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object recordType_ = "";
    /**
     * <code>string recordType = 5;</code>
     * @return The recordType.
     */
    public java.lang.String getRecordType() {
      java.lang.Object ref = recordType_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        recordType_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string recordType = 5;</code>
     * @return The bytes for recordType.
     */
    public com.google.protobuf.ByteString
        getRecordTypeBytes() {
      java.lang.Object ref = recordType_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        recordType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string recordType = 5;</code>
     * @param value The recordType to set.
     * @return This builder for chaining.
     */
    public Builder setRecordType(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      recordType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string recordType = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearRecordType() {
      
      recordType_ = getDefaultInstance().getRecordType();
      onChanged();
      return this;
    }
    /**
     * <code>string recordType = 5;</code>
     * @param value The bytes for recordType to set.
     * @return This builder for chaining.
     */
    public Builder setRecordTypeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      recordType_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object value_ = "";
    /**
     * <code>string value = 6;</code>
     * @return The value.
     */
    public java.lang.String getValue() {
      java.lang.Object ref = value_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        value_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string value = 6;</code>
     * @return The bytes for value.
     */
    public com.google.protobuf.ByteString
        getValueBytes() {
      java.lang.Object ref = value_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        value_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string value = 6;</code>
     * @param value The value to set.
     * @return This builder for chaining.
     */
    public Builder setValue(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      value_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string value = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearValue() {
      
      value_ = getDefaultInstance().getValue();
      onChanged();
      return this;
    }
    /**
     * <code>string value = 6;</code>
     * @param value The bytes for value to set.
     * @return This builder for chaining.
     */
    public Builder setValueBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      value_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.ubiqube.etsi.mano.dns.api.AddHostResponse)
  }

  // @@protoc_insertion_point(class_scope:com.ubiqube.etsi.mano.dns.api.AddHostResponse)
  private static final com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse();
  }

  public static com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AddHostResponse>
      PARSER = new com.google.protobuf.AbstractParser<AddHostResponse>() {
    @java.lang.Override
    public AddHostResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new AddHostResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AddHostResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AddHostResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.ubiqube.etsi.mano.dns.api.v1.AddHostResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

