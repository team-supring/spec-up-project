'use client';

import React from 'react';

import Image from 'next/image';

interface LogoProps {
  className?: string;
  size?: 'sm' | 'md' | 'lg';
}

export default function Logo({ className = '', size = 'md' }: LogoProps) {
  const sizeClasses = {
    sm: {
      icon: 'w-24 h-24',
      container: 'w-24 h-24'
    },
    md: {
      icon: 'w-64 h-28',
      container: 'w-64 h-28'
    },
    lg: {
      icon: 'w-80 h-32',
      container: 'w-80 h-32'
    }
  };

  const currentSize = sizeClasses[size];

  return (
    <div className={`flex items-center ${className}`}>
      {/* 로고 이미지 */}
      <div className={`${currentSize.container} relative`}>
        <Image
          src="/icon/header.icon.png"
          alt="DIV 로고"
          fill
          className="object-contain"
          sizes={currentSize.icon}
        />
      </div>
    </div>
  );
}
