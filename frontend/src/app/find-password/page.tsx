'use client';

import React, { useState } from 'react';

import {
  ArrowLeft,
  CheckCircle,
  Eye,
  EyeOff,
  Lock,
  Mail,
  Phone,
} from 'lucide-react';
import Link from 'next/link';

export default function FindPasswordPage() {
  const [method, setMethod] = useState<'email' | 'phone'>('email');
  const [step, setStep] = useState<'input' | 'verification' | 'reset' | 'complete'>('input');
  const [formData, setFormData] = useState({
    email: '',
    phone: '',
    name: '',
    verificationCode: '',
    newPassword: '',
    confirmPassword: ''
  });
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('비밀번호 찾기:', { method, ...formData });
    
    if (step === 'input') {
      // TODO: 실제 인증 코드 발송 로직 구현
      setStep('verification');
    } else if (step === 'verification') {
      // TODO: 실제 인증 코드 확인 로직 구현
      setStep('reset');
    } else if (step === 'reset') {
      // TODO: 실제 비밀번호 재설정 로직 구현
      setStep('complete');
    }
  };

  const handleReset = () => {
    setStep('input');
    setFormData({ email: '', phone: '', name: '', verificationCode: '', newPassword: '', confirmPassword: '' });
  };

  const isPasswordValid = () => {
    return formData.newPassword && 
           formData.confirmPassword && 
           formData.newPassword === formData.confirmPassword &&
           formData.newPassword.length >= 8;
  };

  if (step === 'complete') {
    return (
      <div className="min-h-screen bg-backgroundMuted flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
        <div className="max-w-md w-full space-y-8">
          <div className="bg-white rounded-2xl shadow-lg p-8 text-center">
            <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-6">
              <CheckCircle className="h-8 w-8 text-green-600" />
            </div>
            
            <h1 className="text-2xl font-bold text-text-primary mb-4">
              비밀번호 재설정 완료
            </h1>
            
            <p className="text-text-secondary mb-6">
              새로운 비밀번호로 로그인할 수 있습니다.
            </p>
            
            <div className="space-y-3">
              <Link
                href="/login"
                className="block w-full bg-brand-primary hover:bg-brand-primary/90 text-white font-medium py-3 px-4 rounded-lg transition-colors duration-200"
              >
                로그인하기
              </Link>
              
              <button
                onClick={handleReset}
                className="w-full bg-gray-100 hover:bg-gray-200 text-text-primary font-medium py-3 px-4 rounded-lg transition-colors duration-200"
              >
                다시 찾기
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-backgroundMuted flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        {/* 비밀번호 찾기 카드 */}
        <div className="bg-white rounded-2xl shadow-lg p-8">
          {/* 제목 */}
          <div className="text-center mb-8">
            <h1 className="text-3xl font-bold text-text-primary mb-2">
              비밀번호 찾기
            </h1>
            <p className="text-text-secondary">
              {step === 'input' && '가입 시 등록한 정보로 비밀번호를 재설정할 수 있습니다'}
              {step === 'verification' && '발송된 인증 코드를 입력해주세요'}
              {step === 'reset' && '새로운 비밀번호를 입력해주세요'}
            </p>
          </div>

          {/* 방법 선택 (첫 단계에서만 표시) */}
          {step === 'input' && (
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
          )}

          {/* 폼 */}
          <form onSubmit={handleSubmit} className="space-y-4">
            {/* 첫 단계: 기본 정보 입력 */}
            {step === 'input' && (
              <>
                <div>
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
              </>
            )}

            {/* 두 번째 단계: 인증 코드 입력 */}
            {step === 'verification' && (
              <div>
                <input
                  type="text"
                  name="verificationCode"
                  value={formData.verificationCode}
                  onChange={handleInputChange}
                  placeholder="인증 코드 6자리"
                  className="w-full pl-4 pr-4 py-3 bg-gray-100 border border-gray-200 rounded-lg text-text-primary placeholder-text-muted focus:outline-none focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/20 transition-all duration-200 text-center text-lg tracking-widest"
                  maxLength={6}
                  required
                />
                <p className="text-sm text-text-muted mt-2 text-center">
                  {method === 'email' ? '이메일' : '전화번호'}로 발송된 인증 코드를 입력해주세요
                </p>
              </div>
            )}

            {/* 세 번째 단계: 새 비밀번호 입력 */}
            {step === 'reset' && (
              <>
                <div>
                  <div className="relative">
                    <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-icon-muted h-4 w-4" />
                    <input
                      type={showPassword ? 'text' : 'password'}
                      name="newPassword"
                      value={formData.newPassword}
                      onChange={handleInputChange}
                      placeholder="새 비밀번호 (8자 이상)"
                      className="w-full pl-10 pr-12 py-3 bg-gray-100 border border-gray-200 rounded-lg text-text-primary placeholder-text-muted focus:outline-none focus:border-brand-primary focus:ring-2 focus:ring-brand-primary/20 transition-all duration-200"
                      required
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute right-3 top-1/2 transform -translate-y-1/2 text-icon-muted hover:text-icon-default transition-colors duration-200"
                    >
                      {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                    </button>
                  </div>
                </div>

                <div>
                  <div className="relative">
                    <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-icon-muted h-4 w-4" />
                    <input
                      type={showConfirmPassword ? 'text' : 'password'}
                      name="confirmPassword"
                      value={formData.confirmPassword}
                      onChange={handleInputChange}
                      placeholder="새 비밀번호 확인"
                      className={`w-full pl-10 pr-12 py-3 bg-gray-100 border rounded-lg text-text-primary placeholder-text-muted focus:outline-none focus:ring-2 transition-all duration-200 ${
                        formData.confirmPassword && formData.newPassword !== formData.confirmPassword
                          ? 'border-red-500 focus:border-red-500 focus:ring-red-200'
                          : 'border-gray-200 focus:border-brand-primary focus:ring-brand-primary/20'
                      }`}
                      required
                    />
                    <button
                      type="button"
                      onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                      className="absolute right-3 top-1/2 transform -translate-y-1/2 text-icon-muted hover:text-icon-default transition-colors duration-200"
                    >
                      {showConfirmPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                    </button>
                  </div>
                  {formData.confirmPassword && formData.newPassword !== formData.confirmPassword && (
                    <p className="text-red-500 text-sm mt-1">비밀번호가 일치하지 않습니다.</p>
                  )}
                </div>
              </>
            )}

            {/* 버튼 */}
            <button
              type="submit"
              disabled={step === 'reset' && !isPasswordValid()}
              className="w-full bg-brand-primary hover:bg-brand-primary/90 disabled:bg-gray-300 disabled:cursor-not-allowed text-white font-medium py-3 px-4 rounded-lg transition-colors duration-200 mt-6"
            >
              {step === 'input' && '인증 코드 발송'}
              {step === 'verification' && '인증 코드 확인'}
              {step === 'reset' && '비밀번호 재설정'}
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
            <Link href="/find-id" className="hover:text-brand-primary transition-colors duration-200">
              아이디 찾기
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}


