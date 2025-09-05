/**
 * 소셜 로그인 API 서비스
 * 카카오, 구글, 네이버, 애플 로그인을 위한 함수들을 제공합니다.
 */

// 소셜 로그인 제공자 타입
export type SocialProvider = 'kakao' | 'google' | 'naver' | 'apple';

// 소셜 로그인 응답 타입
export interface SocialAuthResponse {
  success: boolean;
  data?: {
    accessToken: string;
    refreshToken: string;
    user: {
      id: string;
      email: string;
      name: string;
      profileImage?: string;
    };
  };
  error?: string;
}

// 소셜 로그인 설정
const SOCIAL_CONFIG = {
  kakao: {
    clientId: process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID || '',
    redirectUri: process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI || '',
    authUrl: 'https://kauth.kakao.com/oauth/authorize',
  },
  google: {
    clientId: process.env.NEXT_PUBLIC_GOOGLE_CLIENT_ID || '',
    redirectUri: process.env.NEXT_PUBLIC_GOOGLE_REDIRECT_URI || '',
    authUrl: 'https://accounts.google.com/o/oauth2/v2/auth',
  },
  naver: {
    clientId: process.env.NEXT_PUBLIC_NAVER_CLIENT_ID || '',
    redirectUri: process.env.NEXT_PUBLIC_NAVER_REDIRECT_URI || '',
    authUrl: 'https://nid.naver.com/oauth2.0/authorize',
  },
  apple: {
    clientId: process.env.NEXT_PUBLIC_APPLE_CLIENT_ID || '',
    redirectUri: process.env.NEXT_PUBLIC_APPLE_REDIRECT_URI || '',
    authUrl: 'https://appleid.apple.com/auth/authorize',
  },
};

/**
 * 소셜 로그인 URL 생성
 * @param provider 소셜 로그인 제공자
 * @returns 소셜 로그인 URL
 */
export function getSocialLoginUrl(provider: SocialProvider): string {
  const config = SOCIAL_CONFIG[provider];
  
  if (!config.clientId || !config.redirectUri) {
    throw new Error(`${provider} 로그인 설정이 완료되지 않았습니다.`);
  }

  const params = new URLSearchParams({
    client_id: config.clientId,
    redirect_uri: config.redirectUri,
    response_type: 'code',
    scope: getScopeByProvider(provider),
    state: generateRandomState(),
  });

  return `${config.authUrl}?${params.toString()}`;
}

/**
 * 제공자별 스코프 반환
 */
function getScopeByProvider(provider: SocialProvider): string {
  switch (provider) {
    case 'kakao':
      return 'profile_nickname profile_image account_email';
    case 'google':
      return 'openid email profile';
    case 'naver':
      return 'name email profile_image';
    case 'apple':
      return 'name email';
    default:
      return '';
  }
}

/**
 * 랜덤 state 생성 (CSRF 방지)
 */
function generateRandomState(): string {
  return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
}

/**
 * 소셜 로그인 콜백 처리
 * @param provider 소셜 로그인 제공자
 * @param code 인증 코드
 * @param state state 값
 * @returns 로그인 결과
 */
export async function handleSocialLoginCallback(
  provider: SocialProvider,
  code: string,
  state: string
): Promise<SocialAuthResponse> {
  try {
    // TODO: 실제 백엔드 API 호출로 대체
    const response = await fetch('/api/auth/social/callback', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        provider,
        code,
        state,
      }),
    });

    if (!response.ok) {
      throw new Error('소셜 로그인 처리 중 오류가 발생했습니다.');
    }

    return await response.json();
  } catch (error) {
    console.error(`${provider} 로그인 콜백 처리 오류:`, error);
    return {
      success: false,
      error: error instanceof Error ? error.message : '알 수 없는 오류가 발생했습니다.',
    };
  }
}

/**
 * 소셜 로그인 실행
 * @param provider 소셜 로그인 제공자
 */
export function initiateSocialLogin(provider: SocialProvider): void {
  try {
    const loginUrl = getSocialLoginUrl(provider);
    
    // 팝업으로 열기 (권장)
    const popup = window.open(
      loginUrl,
      `${provider}_login`,
      'width=500,height=600,scrollbars=yes,resizable=yes'
    );

    if (!popup) {
      // 팝업이 차단된 경우 현재 창에서 이동
      window.location.href = loginUrl;
      return;
    }

    // 팝업에서 로그인 완료 후 부모 창으로 메시지 전송
    window.addEventListener('message', (event) => {
      if (event.data.type === 'SOCIAL_LOGIN_SUCCESS') {
        // 로그인 성공 처리
        console.log('소셜 로그인 성공:', event.data);
        popup?.close();
        // TODO: 로그인 성공 후 처리 (리다이렉트, 상태 업데이트 등)
      }
    });
  } catch (error) {
    console.error(`${provider} 로그인 초기화 오류:`, error);
    alert('소셜 로그인을 시작할 수 없습니다. 잠시 후 다시 시도해주세요.');
  }
}

/**
 * 소셜 로그인 토큰 검증
 * @param accessToken 액세스 토큰
 * @returns 토큰 검증 결과
 */
export async function validateSocialToken(accessToken: string): Promise<boolean> {
  try {
    // TODO: 실제 백엔드 API 호출로 대체
    const response = await fetch('/api/auth/validate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    });

    return response.ok;
  } catch (error) {
    console.error('토큰 검증 오류:', error);
    return false;
  }
}

/**
 * 소셜 로그인 로그아웃
 * @param provider 소셜 로그인 제공자
 * @param accessToken 액세스 토큰
 */
export async function logoutSocial(provider: SocialProvider, accessToken: string): Promise<void> {
  try {
    // TODO: 실제 백엔드 API 호출로 대체
    await fetch('/api/auth/logout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
      body: JSON.stringify({ provider }),
    });
  } catch (error) {
    console.error(`${provider} 로그아웃 오류:`, error);
  }
}

/**
 * 소셜 계정 연동 해제
 * @param provider 소셜 로그인 제공자
 * @param accessToken 액세스 토큰
 */
export async function unlinkSocial(provider: SocialProvider, accessToken: string): Promise<boolean> {
  try {
    // TODO: 실제 백엔드 API 호출로 대체
    const response = await fetch('/api/auth/unlink', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
      body: JSON.stringify({ provider }),
    });

    return response.ok;
  } catch (error) {
    console.error(`${provider} 계정 연동 해제 오류:`, error);
    return false;
  }
}

