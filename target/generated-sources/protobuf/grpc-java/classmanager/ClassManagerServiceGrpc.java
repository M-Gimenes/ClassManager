package classmanager;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: classmanager.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ClassManagerServiceGrpc {

  private ClassManagerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "classmanager.ClassManagerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      classmanager.Classmanager.MemberNames> getGetGroupMembersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGroupMembers",
      requestType = com.google.protobuf.Empty.class,
      responseType = classmanager.Classmanager.MemberNames.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      classmanager.Classmanager.MemberNames> getGetGroupMembersMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, classmanager.Classmanager.MemberNames> getGetGroupMembersMethod;
    if ((getGetGroupMembersMethod = ClassManagerServiceGrpc.getGetGroupMembersMethod) == null) {
      synchronized (ClassManagerServiceGrpc.class) {
        if ((getGetGroupMembersMethod = ClassManagerServiceGrpc.getGetGroupMembersMethod) == null) {
          ClassManagerServiceGrpc.getGetGroupMembersMethod = getGetGroupMembersMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, classmanager.Classmanager.MemberNames>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetGroupMembers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  classmanager.Classmanager.MemberNames.getDefaultInstance()))
              .setSchemaDescriptor(new ClassManagerServiceMethodDescriptorSupplier("GetGroupMembers"))
              .build();
        }
      }
    }
    return getGetGroupMembersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      classmanager.Classmanager.StudentList> getListStudentsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListStudents",
      requestType = com.google.protobuf.Empty.class,
      responseType = classmanager.Classmanager.StudentList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      classmanager.Classmanager.StudentList> getListStudentsMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, classmanager.Classmanager.StudentList> getListStudentsMethod;
    if ((getListStudentsMethod = ClassManagerServiceGrpc.getListStudentsMethod) == null) {
      synchronized (ClassManagerServiceGrpc.class) {
        if ((getListStudentsMethod = ClassManagerServiceGrpc.getListStudentsMethod) == null) {
          ClassManagerServiceGrpc.getListStudentsMethod = getListStudentsMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, classmanager.Classmanager.StudentList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListStudents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  classmanager.Classmanager.StudentList.getDefaultInstance()))
              .setSchemaDescriptor(new ClassManagerServiceMethodDescriptorSupplier("ListStudents"))
              .build();
        }
      }
    }
    return getListStudentsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ClassManagerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClassManagerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClassManagerServiceStub>() {
        @java.lang.Override
        public ClassManagerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClassManagerServiceStub(channel, callOptions);
        }
      };
    return ClassManagerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ClassManagerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClassManagerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClassManagerServiceBlockingStub>() {
        @java.lang.Override
        public ClassManagerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClassManagerServiceBlockingStub(channel, callOptions);
        }
      };
    return ClassManagerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ClassManagerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClassManagerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClassManagerServiceFutureStub>() {
        @java.lang.Override
        public ClassManagerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClassManagerServiceFutureStub(channel, callOptions);
        }
      };
    return ClassManagerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getGroupMembers(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<classmanager.Classmanager.MemberNames> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetGroupMembersMethod(), responseObserver);
    }

    /**
     */
    default void listStudents(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<classmanager.Classmanager.StudentList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListStudentsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ClassManagerService.
   */
  public static abstract class ClassManagerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ClassManagerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ClassManagerService.
   */
  public static final class ClassManagerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ClassManagerServiceStub> {
    private ClassManagerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClassManagerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClassManagerServiceStub(channel, callOptions);
    }

    /**
     */
    public void getGroupMembers(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<classmanager.Classmanager.MemberNames> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetGroupMembersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listStudents(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<classmanager.Classmanager.StudentList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListStudentsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ClassManagerService.
   */
  public static final class ClassManagerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ClassManagerServiceBlockingStub> {
    private ClassManagerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClassManagerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClassManagerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public classmanager.Classmanager.MemberNames getGroupMembers(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetGroupMembersMethod(), getCallOptions(), request);
    }

    /**
     */
    public classmanager.Classmanager.StudentList listStudents(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListStudentsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ClassManagerService.
   */
  public static final class ClassManagerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ClassManagerServiceFutureStub> {
    private ClassManagerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClassManagerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClassManagerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<classmanager.Classmanager.MemberNames> getGroupMembers(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetGroupMembersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<classmanager.Classmanager.StudentList> listStudents(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListStudentsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_GROUP_MEMBERS = 0;
  private static final int METHODID_LIST_STUDENTS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_GROUP_MEMBERS:
          serviceImpl.getGroupMembers((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<classmanager.Classmanager.MemberNames>) responseObserver);
          break;
        case METHODID_LIST_STUDENTS:
          serviceImpl.listStudents((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<classmanager.Classmanager.StudentList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetGroupMembersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              classmanager.Classmanager.MemberNames>(
                service, METHODID_GET_GROUP_MEMBERS)))
        .addMethod(
          getListStudentsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              classmanager.Classmanager.StudentList>(
                service, METHODID_LIST_STUDENTS)))
        .build();
  }

  private static abstract class ClassManagerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ClassManagerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return classmanager.Classmanager.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ClassManagerService");
    }
  }

  private static final class ClassManagerServiceFileDescriptorSupplier
      extends ClassManagerServiceBaseDescriptorSupplier {
    ClassManagerServiceFileDescriptorSupplier() {}
  }

  private static final class ClassManagerServiceMethodDescriptorSupplier
      extends ClassManagerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ClassManagerServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ClassManagerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ClassManagerServiceFileDescriptorSupplier())
              .addMethod(getGetGroupMembersMethod())
              .addMethod(getListStudentsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
