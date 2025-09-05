import './globals.css';

import type { Metadata } from 'next';
import { Noto_Sans_KR } from 'next/font/google';

import AppLayout from '@/components/layout/AppLayout';

const notoSansKr = Noto_Sans_KR({ subsets: ['latin'], weight: ['400','500','700'] })

export const metadata: Metadata = {
  title: '동네생활 - 동네에서 찾는 모든 것',
  description: '중고거래부터 동네생활까지, 당신의 동네를 더 풍요롭게',
  keywords: '동네생활, 중고거래, 동네업체, 부동산, 알바, 모임',
  authors: [{ name: '동네생활 팀' }],
  viewport: 'width=device-width, initial-scale=1',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="ko">
      <body className={notoSansKr.className}>
        <AppLayout>
          {children}
        </AppLayout>
      </body>
    </html>
  )
}
