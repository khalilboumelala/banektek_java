//inspiration from https://www.youtube.com/watch?v=ZaG_Hwxli78

//grab the SVG
const pieCharts = document.querySelectorAll("[data-pie]");

//If user has set there browser to reduce web animations
const preferReducedMotion = window.matchMedia(
	"(prefers-reduced-motion: reduce)"
).matches;

//Make sure all elements are visible, before triggering animations
const pieChartObserver = new IntersectionObserver(
	(entries) => {
		entries.forEach((chart) => {
			if (chart.isIntersecting) {
				let speed = +chart.target.dataset.speed || 0;
				preferReducedMotion && (speed = 0);
				const delay = +chart.target.dataset.delay || 0;
				const percent = +chart.target.dataset.percent || 0;
				const circle = chart.target.querySelector("circle");
				const icon = chart.target.querySelector("i");
				const text = chart.target.querySelector("text");
				text.textContent = `${percent}%`;
				chart.target.setAttribute("aria-label", `${percent} percent pie chart`);
				icon.animate([{ opacity: 0 }, { opacity: 1 }], {
					duration: 1000,
					easing: "cubic-bezier(0.57,-0.04, 0.41, 1.13)",
					fill: "forwards"
				});
				circle.animate(
					[
						{
							strokeDashoffset: 100
						},
						{
							strokeDashoffset: 100 - percent
						}
					],
					{
						duration: speed,
						easing: "cubic-bezier(0.57,-0.04, 0.41, 1.13)",
						fill: "forwards"
					}
				);
				text.animate(
					[
						{
							opacity: 0,
							transform: "translateY(20%)"
						},
						{
							opacity: 1,
							transform: "translateY(18%)"
						}
					],
					{
						delay: preferReducedMotion ? 0 : delay,
						duration: preferReducedMotion ? 0 : 300,
						easing: "cubic-bezier(0.57,-0.04, 0.41, 1.13)",
						fill: "forwards"
					}
				);
				pieChartObserver.unobserve(chart.target);
			}
		});
	},
	{
		threshold: 0.8
	}
);

pieCharts.forEach((chart) => {
	pieChartObserver.observe(chart);
});

//Hover Effect
const container = document.querySelector("[data-container]");
const graphic = document.querySelector("[data-graphic]");

//Moving Animation Event
container.addEventListener("mousemove", (e) => {
	let xAxis = (window.innerWidth / 2 - e.pageX) / 25;
	let yAxis = (window.innerHeight / 2 - e.pageY) / 25;
	graphic.style.transform = `rotateY(${xAxis}deg) rotateX(${yAxis}deg)`;
});
//Animate In
container.addEventListener("mouseenter", (e) => {
	graphic.style.transition = "none";
});
//Animate Out
container.addEventListener("mouseleave", (e) => {
	graphic.style.transition = "all 0.5s ease";
	graphic.style.transform = `rotateY(0deg) rotateX(0deg)`;
});
