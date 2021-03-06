package com.mqunar.contacts.basis.db;

import com.mqunar.contacts.basis.model.Contact;
import java.util.List;

public interface ContactsMakeDiffCallback {
    void onFailure(ReadContactsError readContactsError);

    void onSuccess(List<Contact> list);
}
