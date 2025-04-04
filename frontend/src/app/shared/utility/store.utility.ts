export function handleReducerLoading<T>(
  loadingState: T[],
  loadingKey: T,
  isLoading: boolean
) {
  return isLoading
    ? [...loadingState, loadingKey]
    : loadingState.filter((key) => key !== loadingKey);
}
