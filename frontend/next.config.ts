import type { NextConfig } from 'next';

const nextConfig: NextConfig = {
  // 개발 환경에서 백엔드 API 프록시 설정
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8080/api/:path*',
      },
    ]
  },
  // 개발 서버 설정
  experimental: {
    turbo: {
      rules: {
        '*.svg': {
          loaders: ['@svgr/webpack'],
          as: '*.js',
        },
      },
    },
  },
}

export default nextConfig
