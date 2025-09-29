import authApi from "../baseApi";

export const login = async (memberId: string, memberPassword: string) => {
    try {

        console.log(memberId, memberPassword);
        const response = await authApi.post("auth/login", {
            memberId,
            memberPassword,
        });

        if (response?.status === 200) {
            const accessToken = response.headers["authorization"];

            if (accessToken) {
                const tokenWithoutBearer = accessToken.replace("Bearer ", "");
                localStorage.setItem("accessToken", tokenWithoutBearer);
            } else {
                console.error("AccessToken이 존재하지 않습니다.");
                throw new Error(
                    "로그인 실패: AccessToken이 존재하지 않습니다."
                );
            }
        }
    } catch (error: any) {
        console.error("로그인 중 에러 발생:", error);

        if (!error.response) {
            console.error("응답이 없습니다. 네트워크 오류일 수 있습니다.");
            throw new Error("네트워크 오류: 서버와의 연결에 실패했습니다.");
        }

        if (error.response.status === 403) {
            console.error("아이디 또는 비밀번호가 틀렸습니다.");
            throw new Error("로그인 실패: 아이디와 비밀번호를 확인해주세요.");
        }

        throw new Error(`서버 에러: ${error.response.status}`);
    }
};

export const signup = async (
    memberId: string,
    memberPassword: string,
    name: string,
    email: string,
    birth: string,
    sex: string
) => {
    const response = await authApi.post("auth/signup", {
        memberId,
        memberPassword,
        name,
        email,
        birth,
        sex,
    });
    return response.data;
};

export const checkLoginIdExists = async (memberId: string) => {
    const response = await authApi.get(`auth/exists/${memberId}`);
    return response.data;
};

export const logout = async () => {
    const response = await authApi.post("auth/logout");
    return response.data;
};
