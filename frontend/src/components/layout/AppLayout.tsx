'use client';

import { ReactNode } from 'react';

import Link from 'next/link';

import Logo from '../common/Logo';

interface AppLayoutProps {
  children: ReactNode;
}

export default function AppLayout({ children }: AppLayoutProps) {
  return (
    <div className="min-h-screen bg-gray-50">
      {/* 헤더 */}
      <header className="bg-white sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-20">
            {/* 로고 */}
            <div className="flex items-center">
              <Link href="/" className="flex items-center">
                <Logo size="md" />
              </Link>
            </div>

            {/* 우측 메뉴 */}
            <div className="flex items-center space-x-8">
              <div className="flex items-center space-x-6">
                <Link 
                  href="/login" 
                  className="text-gray-700 hover:text-blue-600 transition-colors duration-200 px-3 py-2 rounded-lg hover:bg-blue-50 text-sm"
                >
                  로그인
                </Link>
                <span className="text-gray-400">•</span>
                <Link 
                  href="/signup" 
                  className="text-gray-700 hover:text-blue-600 transition-colors duration-200 px-3 py-2 rounded-lg hover:bg-blue-50 text-sm"
                >
                  회원가입
                </Link>
                <span className="text-gray-400">•</span>
                <Link 
                  href="/mypage" 
                  className="text-gray-700 hover:text-blue-600 transition-colors duration-200 px-3 py-2 rounded-lg hover:bg-blue-50 text-sm"
                >
                  마이페이지
                </Link>
              </div>
            </div>
          </div>
        </div>
      </header>

      {/* 메인 콘텐츠 */}
      <main className="flex-1">
        {children}
      </main>

      {/* 푸터 */}
      <footer className="bg-white border-t border-gray-200 py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          {/* 소개 */}
          <div className="mb-6">
            <p className="text-center text-gray-600 max-w-2xl mx-auto">
              동네 이웃과 함께하는 안전한 중고거래 플랫폼입니다.
              믿을 수 있는 거래 환경을 제공합니다.
            </p>
          </div>

          {/* (주)DIV 사업자 정보 */}
          <div className="bg-gray-50 rounded-lg p-4 mb-6">
            <h3 className="font-semibold text-gray-900 mb-2">(주)DIV 사업자 정보</h3>
            <p className="text-sm text-gray-600 mb-1">(주)DIV 주소 : 서울시 종로구 창경궁로 254 동원빌딩 7층</p>
            <p className="text-sm text-gray-600">대표번호 : 02-2188-6900</p>
          </div>

          {/* Contact */}
          <div className="text-center mb-6">
            <div className="flex items-center justify-center space-x-4 text-sm text-gray-600 mb-2">
              <a href="#" className="hover:text-blue-600 transition-colors">DIV 셀러 회원 신청</a>
              <span>|</span>
              <a href="#" className="hover:text-blue-600 transition-colors">광고 문의</a>
            </div>
          </div>

          {/* 고객센터 */}
          <div className="text-center mb-6">
            <div className="text-sm text-gray-600">
              <a href="#" className="hover:text-blue-600 transition-colors">공지사항</a>
            </div>
          </div>

          {/* 정책 링크 */}
          <div className="text-center mb-6">
            <div className="flex flex-wrap items-center justify-center space-x-4 text-xs text-gray-500">
              <a href="#" className="hover:text-blue-600 transition-colors">이용약관</a>
              <span>|</span>
              <a href="#" className="hover:text-blue-600 transition-colors">개인정보처리방침</a>
              <span>|</span>
              <a href="#" className="hover:text-blue-600 transition-colors">분쟁처리절차</a>
              <span>|</span>
              <a href="#" className="hover:text-blue-600 transition-colors">청소년보호정책</a>
              <span>|</span>
              <a href="#" className="hover:text-blue-600 transition-colors">사업자정보확인</a>
              <span>|</span>
              <a href="#" className="hover:text-blue-600 transition-colors">게시글 수집 및 이용 안내</a>
            </div>
          </div>

          {/* 저작권 및 면책조항 */}
          <div className="text-center">
            <p className="text-xs text-gray-500 mb-2">
              © 2024 (주)DIV. All rights reserved.
            </p>
            <p className="text-xs text-gray-500 max-w-4xl mx-auto leading-relaxed">
              상점의 판매상품을 제외한 모든 상품들에 대하여, (주)DIV는 통신판매중계자로서 당사자가 아니며 판매 회원과 구매 회원 간의 상품거래 정보 및 거래에 관여하지 않고, 어떠한 의무와 책임도 부담하지 않습니다.
            </p>
          </div>
        </div>
      </footer>
    </div>
  );
}

