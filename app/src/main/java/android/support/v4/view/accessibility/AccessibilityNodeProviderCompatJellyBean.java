package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

/* loaded from: classes.dex */
class AccessibilityNodeProviderCompatJellyBean {

    interface AccessibilityNodeInfoBridge {
        Object createAccessibilityNodeInfo(int r1);

        List<Object> findAccessibilityNodeInfosByText(String str, int r2);

        boolean performAction(int r1, int r2, Bundle bundle);
    }

    AccessibilityNodeProviderCompatJellyBean() {
    }

    public static Object newAccessibilityNodeProviderBridge(final AccessibilityNodeInfoBridge bridge) {
        return new AccessibilityNodeProvider() { // from class: android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean.1
            @Override // android.view.accessibility.AccessibilityNodeProvider
            public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
                return (AccessibilityNodeInfo) AccessibilityNodeInfoBridge.this.createAccessibilityNodeInfo(virtualViewId);
            }

            @Override // android.view.accessibility.AccessibilityNodeProvider
            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
                return AccessibilityNodeInfoBridge.this.findAccessibilityNodeInfosByText(text, virtualViewId);
            }

            @Override // android.view.accessibility.AccessibilityNodeProvider
            public boolean performAction(int virtualViewId, int action, Bundle arguments) {
                return AccessibilityNodeInfoBridge.this.performAction(virtualViewId, action, arguments);
            }
        };
    }
}
