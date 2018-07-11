/**
 * Created by toren on 11/07/2018.
 */
package zello.handle;

import android.text.Html;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zello.sdk.*;
import ai.kitt.snowboy.*;
import ai.kitt.snowboy.demo.R;

public class ZelloHandler {
    public static void ConnectChannel(String channelName) {
        //Zello.getInstance().connectChannel("Group" + channelName);
        Zello.getInstance().connectChannel("Group20");
    }

    /*private void updateSelectedContact() {
        Contact _selectedContact = new Contact();
        Zello.getInstance().getSelectedContact(_selectedContact);
        boolean selected = _selectedContact.isValid();
        boolean canTalk = false, showConnect = false, connected = false, canConnect = false;
        if (selected) {
            // Update info
            //ListAdapter.configureView(_viewContactInfo, _selectedContact);
            ContactType type = _selectedContact.getType();
            ContactStatus status = _selectedContact.getStatus();
            switch (type) {
                case USER:
                case GATEWAY: {
                    // User or radio gateway
                    canTalk = status != ContactStatus.OFFLINE; // Not offline
                    break;
                }
                case CHANNEL:
                case GROUP:
                case CONVERSATION: {
                    showConnect = !_selectedContact.getNoDisconnect();
                    if (_appState.isSignedIn()) {
                        if (status == ContactStatus.AVAILABLE) {
                            canTalk = true; // Channel is online
                            canConnect = true;
                            connected = true;
                        } else if (status == ContactStatus.OFFLINE) {
                            canConnect = true;
                        } else if (status == ContactStatus.CONNECTING) {
                            connected = true;
                        }
                    }
                    break;
                }
            }
        }
        /*_viewContactNotSelected.setVisibility(selected ? View.INVISIBLE : View.VISIBLE);
        _viewContactInfo.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
        _btnTalk.setEnabled(canTalk);
        _btnConnect.setEnabled(canConnect);
        _btnConnect.setChecked(connected);
        _btnConnect.setVisibility(showConnect ? View.VISIBLE : View.GONE);
        updateAppState();
    }*/
}
