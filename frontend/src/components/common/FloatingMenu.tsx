'use client';

import React from 'react';

import {
  Bot,
  Eye,
  Heart,
  Store,
} from 'lucide-react';

interface FloatingMenuProps {
  className?: string;
}

export default function FloatingMenu({ className = '' }: FloatingMenuProps) {
  const menuItems = [
    {
      icon: Heart,
      text: '찜한 상품',
      href: '/wishlist',
      color: 'text-gray-700'
    },
    {
      icon: Store,
      text: '내 상점',
      href: '/mystore',
      color: 'text-blue-600'
    },
    {
      icon: Eye,
      text: '최근 본 상품',
      href: '/recent',
      color: 'text-gray-700'
    },
    {
      icon: Bot,
      text: '챗봇 연결',
      href: '/chatbot',
      color: 'text-gray-700'
    }
  ];

  return (
    <div className={`fixed right-6 top-1/2 transform -translate-y-1/2 z-50 ${className}`}>
      <div className="bg-white rounded-2xl shadow-2xl p-4 border border-gray-100">
        <div className="flex flex-col space-y-6">
          {menuItems.map((item, index) => {
            const IconComponent = item.icon;
            return (
              <a
                key={index}
                href={item.href}
                className="group flex flex-col items-center space-y-2 transition-all duration-200 hover:scale-110"
              >
                <div className="w-12 h-12 flex items-center justify-center rounded-xl bg-gray-50 group-hover:bg-blue-50 transition-colors duration-200">
                  <IconComponent 
                    className={`w-6 h-6 ${item.color} group-hover:text-blue-600 transition-colors duration-200`}
                  />
                </div>
                <span className={`text-xs font-medium ${item.color} group-hover:text-blue-600 transition-colors duration-200 text-center leading-tight`}>
                  {item.text}
                </span>
              </a>
            );
          })}
        </div>
      </div>
    </div>
  );
}


