import BackendStatus from '@/components/common/BackendStatus';
import FloatingMenu from '@/components/common/FloatingMenu';
import HeroSection from '@/components/home/HeroSection';

export default function Home() {
  return (
    <>
      {/* 백엔드 상태 표시 */}
      <BackendStatus />
      
      {/* 히어로 섹션 */}
      <HeroSection />
      <FloatingMenu />
    </>
  );
}


