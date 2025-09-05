'use client';

import React, { useState } from 'react';

import {
  ArrowLeft,
  CheckCircle,
  Mail,
  Phone,
} from 'lucide-react';
import Link from 'next/link';

export default function FindIdPage() {
  const [method, setMethod] = useState<'email' | 'phone'>('email');
  const [formData, setFormData] = useState({
    email: '',
    phone: '',
    name: ''
  });
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [foundId, setFoundId] = useState('');

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('아이디 찾기:', { method, ...formData });
    
    // TODO: 실제 아이디 찾기 로직 구현
    // 임시로 더미 데이터 사용
    setFoundId('user@example.com');
    setIsSubmitted(true);
  };

  const handleReset = () => {
    setIsSubmitted(false);
    setFoundId('');
    setFormData({ email: '', phone: '', name: '' });
  };

  if (isSubmitted) {
    return (
      <div className="min-h-screen bg-backgroundMuted flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
        <div className="max-w-md w-full space-y-8">
          <div className="bg-white rounded-2xl shadow-lg p-8 text-center">
            <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-6">
              <CheckCircle className="h-8 w-8 text-green-600" />
            </div>
            
            <h1 className="text-2xl font-bold text-text-primary mb-4">
              아이디 찾기 완료
            </h1>
            
            <div className="bg-gray-50 rounded-lg p-4 mb-6">
              <p className="text-text-secondary mb-2">찾은 아이디</p>
              <p className="text-lg font-semibold text-brand-primary">{foundId}</p>
            </div>
            
            <div className="space-y-3">
              <button
                onClick={handleReset}
                className="w-full bg-brand-primary hover:bg-brand-primary/90 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-200"
              >
                다시 찾기
              </button>
              
              <Link
                href="/login"
                className="block w-full bg-gray-100 hover:bg-gray-200 text-text-primary font-medium py-3 px-4 rounded-lg transition-colors duration-200"
              >
                로그인으로 돌아가기
              </Link>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-backgroundMuted flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        {/* 아이디 찾기 카드 */}
        <div className="bg-white rounded-2xl shadow-lg p-8">
          {/* 제목 */}
          <div className="text-center mb-8">
            <h1 className="text-3xl font-bold text-text-primary mb-2">
              아이디 찾기
            </h1>
            <p className="text-text-secondary">
              가입 시 등록한 정보로 아이디를 찾을 수 있습니다
            </p>
          </div>

          {/* 방법 선택 */}
          <div className="mb-6">
            <div className="flex space-x-2 mb-4">
              <button
                onClick={() => setMethod('email')}
                className={`flex-1 py-2 px-4 rounded-lg font-medium transition-colors duration-200 ${
                  method === 'email'
                    ? 'bg-brand-primary text-white'
                    : 'bg-gray-100 text-text-secondary hover:bg-gray-200'
                }`}
              >
                이메일로 찾기
              </button>
              <button
                onClick={() => setMethod('phone')}
                className={`flex-1 py-2 px-4 rounded-lg font-medium transition-colors duration-200 ${
                  method === 'phone'
                    ? 'bg-brand-primary text-white'
                    : 'bg-gray-100 text-text-secondary hover:bg-gray-200'
                }`}
              >
                전화번호로 찾기
              </button>
            </div>
          </div>

          {/* 아이디 찾기 폼 */}
          <form onSubmit={handleSubmit} className="space-y-4">
            {/* 이름 */}
            <div>
              <div className="relative">
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  placeholder="이름"
                  className="w-full pl-4 pr-4 py-3 bg-gray-100 border border-gray-200 rounded-lg text-text-primary placeholder-text-muted focus:outline-none focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/20 transition-all duration-200"
                  required
                />
              </div>
            </div>

            {/* 이메일 또는 전화번호 */}
            {method === 'email' ? (
              <div>
                <div className="relative">
                  <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-icon-muted h-4 w-4" />
                  <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleInputChange}
                    placeholder="이메일"
                    className="w-full pl-10 pr-4 py-3 bg-gray-100 border border-gray-200 rounded-lg text-text-primary placeholder-text-muted focus:outline-none focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/20 transition-all duration-200"
                    required
                  />
                </div>
              </div>
            ) : (
              <div>
                <div className="relative">
                  <Phone className="absolute left-3 top-1/2 transform -translate-y-1/2 text-icon-muted h-4 w-4" />
                  <input
                    type="tel"
                    name="phone"
                    value={formData.phone}
                    onChange={handleInputChange}
                    placeholder="전화번호 (010-1234-5678)"
                    className="w-full pl-10 pr-4 py-3 bg-gray-100 border border-gray-200 rounded-lg text-text-primary placeholder-text-muted focus:outline-none focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/20 transition-all duration-200"
                    required
                  />
                </div>
              </div>
            )}

            {/* 아이디 찾기 버튼 */}
            <button
              type="submit"
              className="w-full bg-brand-primary hover:bg-brand-primary/90 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-200 mt-6"
            >
              아이디 찾기
            </button>
          </form>
        </div>

        {/* 하단 링크 */}
        <div className="text-center space-y-4">
          <div className="flex items-center justify-center space-x-4 text-sm text-text-muted">
            <Link href="/login" className="hover:text-brand-primary transition-colors duration-200 flex items-center">
              <ArrowLeft className="h-4 w-4 mr-1" />
              로그인으로 돌아가기
            </Link>
          </div>
          
          <div className="flex items-center justify-center space-x-4 text-sm text-text-muted">
            <Link href="/signup" className="hover:text-brand-primary transition-colors duration-200">
              회원가입
            </Link>
            <div className="w-px h-4 bg-gray-300"></div>
            <Link href="/find-password" className="hover:text-brand-primary transition-colors duration-200">
              비밀번호 찾기
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}


