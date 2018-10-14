package net.crystalapps.permission.runtime.library.models;

/**
 * Created by Syed Owais Ali on 5/8/2018.
 */

public interface Perm<T> {

    String getType();
    int getRequestCode();
    T getCaller();
}
