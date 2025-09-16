import Image from 'next/image'
import styles from './page.module.css'

export default function SpecUpPage() {
  return (
    <div className={styles.page}>
      {/* 헤더 */}
      <header className={styles.header}>
        <div className={styles.headerInner}>
          <div className={styles.midDivider} aria-hidden="true" />
          <a href="/" className={styles.logo} aria-label="DIV 홈">
            <Image
              src="/logo/div_logo.png"
              alt="DIV 중고거래플랫폼 로고"
              className={styles.logoImg}
              width={218}
              height={218}
              priority
            />
          </a>

          {/* 메뉴 3개 (피그마 y=70, x는 각각 548/715/920) */}
          <nav className={styles.nav} aria-label="주 메뉴">
            <a href="#">내 동네 찾기</a>
            <a href="#">커뮤니티 게시판</a>
            <a href="#">로그아웃</a>
          </nav>

          {/* 검색바 (피그마 X=558, Y=133, W=513, H=60, R=20, Stroke #152257 3px) */}
          <form className={styles.search} role="search" aria-label="검색">
            <input placeholder="검색어를 입력해주세요" />
            <button type="submit" aria-label="검색">🔍</button>
          </form>
        </div>
      </header>

      {/* 오른쪽 퀵바 */}
      <aside className={styles.quickbar} aria-label="quick actions">
        <a href="#">♡<span>관심상품</span></a>
        <a href="#">🖼<span>마이페이지</span></a>
        <a href="#">🕘<span>최근 본 상품</span></a>
        <a href="#" className={styles.bot}>🤖<span>챗봇 연결</span></a>
      </aside>

      {/* 메인(사이드/콘텐츠 컨테이너 자체를 절대좌표로) */}
      <main className={styles.main}>
        {/* 좌측 사이드바 */}
        <aside className={styles.sidebar}>
          <section>
            <h3>마이페이지</h3>
            <ul>
              <li><a href="#">거래정보</a></li>
              <li><a href="#">관심목록</a></li>
              <li><a href="#">구매내역</a></li>
              <li><a href="#">판매내역</a></li>
              <li><a href="#">내 상점</a></li>
            </ul>
          </section>

          <section className={styles.mt32}>
            <h3>내 정보</h3>
            <ul>
              <li><a href="#">회원정보</a></li>
              <li><a href="#">주소관리</a></li>
              <li><a href="#">알림설정</a></li>
            </ul>
          </section>
        </aside>

        {/* 콘텐츠 영역(컨테이너만 절대, 내부는 자유배치) */}
        <section className={styles.content}>
          <div className={styles.shopHeader}>
            <div className={styles.shopTitleRow}>
              <h1 className={styles.shopName}>덕망있는공존</h1>
              <button className={styles.verifyBtn}>닉네임 검증</button>
            </div>

            <div className={styles.ratingRow}>
              <div className={styles.stars} aria-label="별점 3점">★ ★ ★ ☆ ☆</div>
              <div className={styles.score}>거래 점수 <strong>4.33</strong> 😊</div>
            </div>

            <div className={styles.noticeBox}>
              <input placeholder="상점소개를 작성해주세요." />
              <div className={styles.noticeMeta}>
                <span>본인인증 완료</span>
                <span>저장</span>
              </div>
            </div>
          </div>

          {/* 탭 */}
          <div className={styles.tabs}>
            <button className={`${styles.tab} ${styles.active}`}>진행</button>
            <button className={styles.tab}>예약중</button>
            <button className={styles.tab}>판매중</button>
            <button className={styles.tab}>판매완료</button>
          </div>

          {/* 카드 그리드 */}
          <div className={styles.grid}>
            {Array.from({ length: 8 }).map((_, i) => (
              <article key={i} className={styles.card}>
                <div className={styles.thumb}>
                  <img src={`https://picsum.photos/seed/${i}/260/180`} alt="상품 이미지" />
                </div>
                <h4 className={styles.itemTitle}>
                  라탄 원형테이블 급처<br />25,000원
                </h4>
                <div className={styles.metaRow}>
                  <span className={styles.badge}>중고/급처</span>
                  <span className={styles.time}>등록 2시간 전</span>
                </div>
              </article>
            ))}
          </div>
        </section>
      </main>

      {/* 푸터 */}
      <footer className={styles.footer}>
        <div className={styles.footerInner}>
          <div className={styles.company}>
            <strong>(주)DIV 사업자 정보</strong>
            <p>주소: 서울시 금천구 공장공로 25 4동 7층</p>
            <p>대표번호: 02-2188-9801</p>
            <p>Contact: DIV 영업 · 채용 문의</p>
          </div>
          <ul className={styles.footerLinks}>
            <li><a href="#">이용약관</a></li>
            <li><a href="#">개인정보처리방침</a></li>
            <li><a href="#">분쟁조정기준</a></li>
            <li><a href="#">청소년보호정책</a></li>
            <li><a href="#">사업자정보확인</a></li>
            <li><a href="#">게시물 수집 및 이용 안내</a></li>
            <li><a href="#">고객센터</a></li>
            <li><a href="#">공지사항</a></li>
          </ul>
        </div>
      </footer>
    </div>
  )
}


