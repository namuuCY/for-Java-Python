// BOJ 1918 (https://www.acmicpc.net/problem/1918)
// ( A + B * C )
// ( A + (B * C) )
// ( A + BC* )
// ( ABC*+ )
// ABC*+


// A + B * C

// (a+(b+ (c+d) ))
// (a+(b+ cd+ ))
// (a+bcd++ )


// 문자는 큐로,


// 괄호랑 곱셈은 같은건가?

// a x b x c x d x e
// abx x c x d x e
// abx cx x d x e

// abx cx dx x e

// abx cx dx ex


// *, / 는 바로 작업
// ) 도 같이 넣어야 함

// +, -는 스택에
// (+
// (+(
// (+(*
// (+(*)
// (    => *+ 이면 3개
// -
// -(
// -(/
// -(/)
//       =>    /-

// a x (b x c) x d x e

// x
// x(
// x(x
// x(x)
//           abcxx
// x
//          abcxxdx
// x
//          abcxxdxex


// a x (b x c) x d + e

// x
// x(
// x(x
// x(x)
//           abcxx
// x
//          abcxxdx
// +
//          abcxxdxe+

// a + b + (c x d) + e
// +
//     ab+
// +
// +(
// +(*
// +(*)
//       ab+cdx+
// ab+cdxe+

// ( a + b ) * c * ( d + e )

// (
// (+
// (+)
// ab


