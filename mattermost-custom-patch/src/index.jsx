import React from 'react';
import { useEffect } from 'react';

const Patch = ({visible, close, theme, subMenu}) => {
  useEffect(() => {
    // Hide FREE EDITION badge
    const header = document.querySelector('header');
    if (header) {
      const badgeDiv = header.querySelector('div[class^="Badge"]');
      if (badgeDiv) {
        badgeDiv.remove();
      }
    }
  }, []);
  return null;
};

class HeaderPlugin {
    initialize(registry, store) {
      registry.registerRootComponent(Patch);
    }
}

window.registerPlugin('me.sharuru.custom-patch', new HeaderPlugin());
