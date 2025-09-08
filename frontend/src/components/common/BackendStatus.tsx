'use client';

import {
  useEffect,
  useState,
} from 'react';

import {
  CheckCircle,
  Loader2,
  XCircle,
} from 'lucide-react';

import {
  checkBackendStatus,
  testBackendApi,
} from '@/lib/api';

/**
 * 백엔드 연결 상태를 표시하는 컴포넌트
 * 실시간으로 백엔드 서버의 상태를 모니터링
 */
export default function BackendStatus() {
  const [backendStatus, setBackendStatus] = useState<'loading' | 'connected' | 'disconnected'>('loading');
  const [apiTestResult, setApiTestResult] = useState<string>('');

  useEffect(() => {
    // 백엔드 상태 확인
    const checkStatus = async () => {
      try {
        const isConnected = await checkBackendStatus();
        setBackendStatus(isConnected ? 'connected' : 'disconnected');
        
        if (isConnected) {
          // API 테스트 실행
          const result = await testBackendApi();
          if (result.success) {
            setApiTestResult(result.data || 'API 연결 성공!');
          } else {
            setApiTestResult('API 테스트 실패');
          }
        }
      } catch (error) {
        setBackendStatus('disconnected');
        setApiTestResult('연결 오류 발생');
      }
    };

    checkStatus();
    
    // 5초마다 상태 확인
    const interval = setInterval(checkStatus, 5000);
    return () => clearInterval(interval);
  }, []);

  const getStatusIcon = () => {
    switch (backendStatus) {
      case 'loading':
        return <Loader2 className="h-5 w-5 animate-spin text-yellow-500" />;
      case 'connected':
        return <CheckCircle className="h-5 w-5 text-green-500" />;
      case 'disconnected':
        return <XCircle className="h-5 w-5 text-red-500" />;
    }
  };

  const getStatusText = () => {
    switch (backendStatus) {
      case 'loading':
        return '백엔드 연결 확인 중...';
      case 'connected':
        return '백엔드 연결됨';
      case 'disconnected':
        return '백엔드 연결 안됨';
    }
  };

  return (
    <div className="fixed top-4 right-4 z-50 bg-white rounded-lg shadow-lg p-4 border">
      <div className="flex items-center space-x-2">
        {getStatusIcon()}
        <span className="text-sm font-medium">{getStatusText()}</span>
      </div>
      {apiTestResult && (
        <div className="mt-2 text-xs text-gray-600">
          {apiTestResult}
        </div>
      )}
    </div>
  );
}




