// debouncing
export function debounce(func: Function, delay: any) {
  let timer: NodeJS.Timeout;
  return function (...args: any) {
    clearTimeout(timer);

    timer = setTimeout(() => {
      func(...args);
    }, delay);
  };
}
