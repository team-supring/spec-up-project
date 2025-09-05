import {
  ArrowRight,
  Shield,
  ShoppingBag,
  Star,
  Users,
} from 'lucide-react';
import Link from 'next/link';

/**
 * 홈페이지 서비스 카테고리 섹션 컴포넌트
 * 중고거래, 커뮤니티, 동네업체 등의 서비스를 소개
 */
export default function ServiceCategories() {
  const services = [
    {
      icon: ShoppingBag,
      title: '중고거래',
      description: '동네 이웃과 안전하게 중고거래하세요',
      href: '/market',
      color: 'from-yellow-400 to-orange-500'
    },
    {
      icon: Users,
      title: '커뮤니티',
      description: '동네 소식과 정보를 공유하세요',
      href: '/community',
      color: 'from-green-500 to-emerald-600'
    },
    {
      icon: Shield,
      title: '동네업체',
      description: '믿을 수 있는 동네 업체를 찾아보세요',
      href: '/businesses',
      color: 'from-blue-500 to-indigo-600'
    },
    {
      icon: Star,
      title: '안전거래',
      description: '검증된 사용자와 안전하게 거래하세요',
      href: '/safety',
      color: 'from-purple-500 to-pink-600'
    }
  ];

  return (
    <section className="py-20 bg-gray-50">
      <div className="max-w-6xl mx-auto px-6">
        <div className="text-center mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-6">
            중고거래의 모든 것
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            일상생활에 필요한 모든 것을 동네에서 쉽고 편리하게 찾아보세요
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          {services.map((service, index) => (
            <div key={index} className="group text-center">
              <div className="bg-white rounded-3xl p-8 shadow-lg hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2">
                <div className={`w-20 h-20 bg-gradient-to-br ${service.color} rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300`}>
                  <service.icon className="h-10 w-10 text-white" />
                </div>
                <h3 className="text-2xl font-bold text-gray-900 mb-4">{service.title}</h3>
                <p className="text-gray-600 leading-relaxed mb-6">
                  {service.description}
                </p>
                <Link 
                  href={service.href}
                  className="flex items-center justify-center text-blue-600 font-semibold group-hover:text-blue-700 transition-colors duration-300"
                >
                  자세히 보기 <ArrowRight className="ml-2 h-4 w-4 group-hover:translate-x-1 transition-transform duration-300" />
                </Link>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}




