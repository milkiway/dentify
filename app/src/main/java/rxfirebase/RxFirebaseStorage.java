package rxfirebase;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;

import rx.Observable;

/**
 * Created by Nick Moskalenko on 24/05/2016.
 */
public class RxFirebaseStorage {

    @NonNull
    public static Observable<byte[]> getBytes(@NonNull final StorageReference storageRef,
                                              final long maxDownloadSizeBytes) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.getBytes(maxDownloadSizeBytes)));
    }

    @NonNull
    public static Observable<Uri> getDownloadUrl(@NonNull final StorageReference storageRef) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.getDownloadUrl()));
    }

    @NonNull
    public static Observable<FileDownloadTask.TaskSnapshot> getFile(@NonNull final StorageReference storageRef,
                                                                    @NonNull final File destinationFile) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.getFile(destinationFile)));
    }

    @NonNull
    public static Observable<FileDownloadTask.TaskSnapshot> getFile(@NonNull final StorageReference storageRef,
                                                                    @NonNull final Uri destinationUri) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.getFile(destinationUri)));
    }

    @NonNull
    public static Observable<StorageMetadata> getMetadata(@NonNull final StorageReference storageRef) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.getMetadata()));
    }

    @NonNull
    public static Observable<StreamDownloadTask.TaskSnapshot> getStream(@NonNull final StorageReference storageRef) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.getStream()));
    }

    @NonNull
    public static Observable<StreamDownloadTask.TaskSnapshot> getStream(@NonNull final StorageReference storageRef,
                                                                        @NonNull final StreamDownloadTask.StreamProcessor processor) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.getStream(processor)));
    }


    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putBytes(@NonNull final StorageReference storageRef,
                                                               @NonNull final byte[] bytes) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.putBytes(bytes)));
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putBytes(@NonNull final StorageReference storageRef,
                                                               @NonNull final byte[] bytes,
                                                               @NonNull final StorageMetadata metadata) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.putBytes(bytes, metadata)));
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putFile(@NonNull final StorageReference storageRef,
                                                              @NonNull final Uri uri) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.putFile(uri)));
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putFile(@NonNull final StorageReference storageRef,
                                                              @NonNull final Uri uri,
                                                              @NonNull final StorageMetadata metadata) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.putFile(uri, metadata)));
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putFile(@NonNull final StorageReference storageRef,
                                                              @NonNull final Uri uri,
                                                              @NonNull final StorageMetadata metadata,
                                                              @NonNull final Uri existingUploadUri) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.putFile(uri, metadata, existingUploadUri)));
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putStream(@NonNull final StorageReference storageRef,
                                                                @NonNull final InputStream stream,
                                                                @NonNull final StorageMetadata metadata) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.putStream(stream, metadata)));
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putStream(@NonNull final StorageReference storageRef,
                                                                @NonNull final InputStream stream) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.putStream(stream)));
    }

    @NonNull
    public static Observable<StorageMetadata> updateMetadata(@NonNull final StorageReference storageRef,
                                                             @NonNull final StorageMetadata metadata) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.updateMetadata(metadata)));
    }

    @NonNull
    public static Observable<Void> delete(@NonNull final StorageReference storageRef) {
        return Observable.create(subscriber -> RxHandler.assignOnTask(subscriber, storageRef.delete()));
    }


}
