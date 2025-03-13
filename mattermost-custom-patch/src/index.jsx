import React from 'react';
import { useEffect } from 'react';

const Patch = ({visible, close, theme, subMenu}) => {
  useEffect(() => {
    // Hide FREE EDITION badge in header
    const header = document.querySelector('header');
    if (header) {
      const badgeDiv = header.querySelector('div[class^="Badge"]');
      if (badgeDiv) {
        badgeDiv.remove();
      }
    }

    // Remove FREE EDITION menu item
    // Use MutationObserver to detect when the menu appears in the DOM
    const menuObserver = new MutationObserver((mutations) => {
      // Look for menu items that contain "FREE EDITION" text
      const menuItems = document.querySelectorAll('li[role="menuitem"]');
      menuItems.forEach(item => {
        if (item.textContent.includes('FREE EDITION')) {
          item.remove();
        }
      });
    });

    // Start observing the document body for added nodes
    menuObserver.observe(document.body, { childList: true, subtree: true });

    // Cleanup function to disconnect the observer when component unmounts
    return () => {
      menuObserver.disconnect();
    };
  }, []);
  return null;
};

class HeaderPlugin {
    initialize(registry, store) {
      registry.registerRootComponent(Patch);
    }
}

window.registerPlugin('me.sharuru.custom-patch', new HeaderPlugin());
