'use client';

import { useState } from 'react';

import { Search } from 'lucide-react';

/**
 * 간단한 메인 섹션 컴포넌트
 * 하얀색 배경에 제목, 설명, 검색창 포함
 */
export default function HeroSection() {
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    // TODO: 검색 기능 구현
    console.log('검색어:', searchQuery);
  };

  return (
    <section className="bg-white min-h-screen flex items-center justify-center px-4">
      <div className="max-w-2xl w-full text-center">
        <h1 className="text-2xl sm:text-3xl md:text-4xl lg:text-5xl xl:text-6xl font-bold text-gray-900 mb-4 sm:mb-6 md:mb-8 leading-tight">
          동네에서 찾는 모든 것
        </h1>
        
        <p className="text-sm sm:text-base md:text-lg lg:text-xl xl:text-2xl text-gray-600 mb-6 sm:mb-8 md:mb-10 leading-relaxed">
          믿을 수 있는 동네 이웃과 함께하는 안전한 중고거래
        </p>
        
        {/* 검색창 */}
        <form onSubmit={handleSearch} className="flex justify-center">
          <div className="relative max-w-xl w-full">
            <input
              type="text"
              placeholder="검색어를 입력해주세요"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="w-full pl-4 pr-12 py-4 bg-white border-2 border-gray-200 rounded-xl text-gray-900 placeholder-gray-500 focus:outline-none focus:border-blue-500 focus:ring-4 focus:ring-blue-500/20 transition-all duration-300 text-lg shadow-lg"
            />
            <Search className="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
          </div>
        </form>
      </div>
    </section>
  );
}


