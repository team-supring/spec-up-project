'use client';

import React, { useState } from 'react';

import {
  Eye,
  EyeOff,
  Lock,
  Mail,
} from 'lucide-react';
import Link from 'next/link';

import {
  initiateSocialLogin,
  SocialProvider,
} from '@/lib/auth/socialAuth';

export default function LoginPage() {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleEmailLogin = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('이메일 로그인:', formData);
    // TODO: 실제 로그인 로직 구현
  };

  const handleSocialLogin = (provider: SocialProvider) => {
    console.log(`${provider} 로그인 시도`);
    initiateSocialLogin(provider);
  };

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-6">
        {/* 로그인 카드 */}
        <div className="bg-white rounded-2xl shadow-xl p-8">
          {/* 제목 */}
          <div className="text-center mb-8">
            <h1 className="text-3xl font-bold text-gray-900 mb-3">
              로그인
            </h1>
            <p className="text-gray-600 text-lg">
              동네생활에 오신 것을 환영합니다
            </p>
          </div>

          {/* 간편 로그인 */}
          <div className="mb-8">
            <div className="flex items-center justify-center mb-6">
              <span className="bg-blue-100 text-blue-700 text-sm px-4 py-2 rounded-full font-medium">
                간편 로그인
              </span>
            </div>
            
            <div className="space-y-4">
              {/* 카카오 로그인 */}
              <button
                onClick={() => handleSocialLogin('kakao')}
                className="w-full bg-yellow-400 hover:bg-yellow-500 text-black font-medium py-4 px-4 rounded-xl flex items-center justify-center transition-all duration-200 shadow-sm hover:shadow-md"
              >
                <div className="w-6 h-6 bg-black rounded-full mr-3 flex items-center justify-center">
                  <span className="text-yellow-400 text-sm font-bold">K</span>
                </div>
                카카오 계정으로 로그인
              </button>

              {/* 구글 로그인 */}
              <button
                onClick={() => handleSocialLogin('google')}
                className="w-full bg-white hover:bg-gray-50 text-gray-700 font-medium py-4 px-4 rounded-xl border-2 border-gray-200 flex items-center justify-center transition-all duration-200 shadow-sm hover:shadow-md"
              >
                <div className="w-6 h-6 mr-3">
                  <svg viewBox="0 0 24 24" className="w-6 h-6">
                    <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                    <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                    <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                    <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
                  </svg>
                </div>
                구글 계정으로 로그인
              </button>

              {/* 네이버 로그인 */}
              <button
                onClick={() => handleSocialLogin('naver')}
                className="w-full bg-green-500 hover:bg-green-600 text-white font-medium py-4 px-4 rounded-xl flex items-center justify-center transition-all duration-200 shadow-sm hover:shadow-md"
              >
                <div className="w-6 h-6 bg-white text-green-500 rounded mr-3 flex items-center justify-center">
                  <span className="text-green-500 text-sm font-bold">N</span>
                </div>
                네이버 계정으로 로그인
              </button>

              {/* 애플 로그인 */}
              <button
                onClick={() => handleSocialLogin('apple')}
                className="w-full bg-black hover:bg-gray-900 text-white font-medium py-4 px-4 rounded-xl flex items-center justify-center transition-all duration-200 shadow-sm hover:shadow-md"
              >
                <div className="w-6 h-6 mr-3">
                  <svg viewBox="0 0 24 24" className="w-6 h-6 fill-current">
                    <path d="M18.71 19.5c-.83 1.24-1.71 2.45-3.05 2.47-1.34.03-1.77-.79-3.29-.79-1.53 0-2 .77-3.27.82-1.31.05-2.3-1.32-3.14-2.53C4.25 17 2.94 12.45 4.7 9.39c.87-1.52 2.43-2.48 4.12-2.51 1.28-.02 2.5.87 3.29.87.78 0 2.26-1.07 3.81-.91.65.03 2.47.26 3.64 1.98-.09.06-2.17 1.28-2.15 3.81.03 3.02 2.65 4.03 2.68 4.04-.03.07-.42 1.44-1.38 2.83M13 3.5c.73-.83 1.94-1.46 2.94-1.5.13 1.17-.34 2.35-1.04 3.19-.69.85-1.83 1.51-2.95 1.42-.15-1.15.41-2.35 1.05-3.11z"/>
                  </svg>
                </div>
                애플 계정으로 로그인
              </button>
            </div>
          </div>

          {/* 구분선 */}
          <div className="relative mb-8">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-gray-200" />
            </div>
            <div className="relative flex justify-center text-sm">
              <span className="px-4 bg-white text-gray-500 font-medium">또는 직접 입력</span>
            </div>
          </div>

          {/* 이메일/비밀번호 로그인 */}
          <form onSubmit={handleEmailLogin} className="space-y-6">
            <div>
              <div className="relative">
                <Mail className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  placeholder="이메일"
                  className="w-full pl-12 pr-4 py-4 bg-gray-50 border-2 border-gray-200 rounded-xl text-gray-900 placeholder-gray-500 focus:outline-none focus:border-blue-500 focus:ring-4 focus:ring-blue-100 transition-all duration-200"
                  required
                />
              </div>
            </div>

            <div>
              <div className="relative">
                <Lock className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                <input
                  type={showPassword ? 'text' : 'password'}
                  name="password"
                  value={formData.password}
                  onChange={handleInputChange}
                  placeholder="비밀번호"
                  className="w-full pl-12 pr-14 py-4 bg-gray-50 border-2 border-gray-200 rounded-xl text-gray-900 placeholder-gray-500 focus:outline-none focus:border-blue-500 focus:ring-4 focus:ring-blue-100 transition-all duration-200"
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors duration-200"
                >
                  {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
            </div>

            <button
              type="submit"
              className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-4 px-4 rounded-xl transition-all duration-200 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5"
            >
              로그인
            </button>
          </form>
        </div>

        {/* 하단 링크 */}
        <div className="bg-white rounded-xl p-6 shadow-lg">
          <div className="flex flex-col sm:flex-row items-center justify-center space-y-3 sm:space-y-0 sm:space-x-8 text-sm">
            <Link href="/signup" className="text-blue-600 hover:text-blue-700 font-medium transition-colors duration-200">
              회원가입
            </Link>
            <div className="hidden sm:block w-px h-4 bg-gray-300"></div>
            <Link href="/find-id" className="text-gray-600 hover:text-gray-800 transition-colors duration-200">
              아이디 찾기
            </Link>
            <div className="hidden sm:block w-px h-4 bg-gray-300"></div>
            <Link href="/find-password" className="text-gray-600 hover:text-gray-800 transition-colors duration-200">
              비밀번호 찾기
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
